<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 4/20/2018
 * Time: 11:30 AM
 */

namespace App\OTG\Raspberries\Services;


use App\OTG\Firebase\Services\firebaseNotificationsServices;
use App\OTG\Orders\Models\ItemsModel;
use App\OTG\Orders\Models\OrdersModel;
use App\OTG\Orders\Models\ProductsModel;
use App\OTG\Orders\Repositories\ordersRepositories;
use App\OTG\Services;
use App\OTG\Users\Models\Users;
use Illuminate\Support\Facades\Input;

class raspberriesServices extends Services
{
    private $Repository;
    private $Notifier;

    public function __construct()
    {
        $this->Repository = new ordersRepositories();
        $this->Notifier     = new firebaseNotificationsServices('orders');
    }

    /** add new order for user
     * @return \Illuminate\Http\JsonResponse
     */
    public function add(){
        $rules = [
            'user_id' => "required|exists:users,id",
        ];

        $validator = \Validator::make(Input::all(),$rules);

        if($validator->passes()){

            $user_id = (int)Input::get('user_id');

            $order_id = $this->Repository->newOrder($user_id);

            if($order_id){

                if($user = Users::find($user_id)){
                    $data = new \stdClass();

                    $token = $user->remember_token;
                    if(strlen($token)) {
                        $data = new \stdClass();
                        $data->userToken = $token;
                        $data->body = 'Welcome To OTG Store......!';
                        $data->sound = 'default';
                        $data->dataArray = ['data' => ['data array']];

                        $firebase = new firebaseNotificationsServices();

                        $notified = $firebase->notifyUser($data);

                        /**
                         *  Notifications
                         */
                        $this->Notifier->notifyUser($data);

                        $res = [
                            'msg' => "Order Created",
                            "order_id" => $order_id,
                            "notified" => $notified,
                            'error' => 0
                        ];

                        return response()->json($res, 200);
                    } else {
                        $res = [
                            'msg' =>"This User Doesn't have firebase token",
                            'error' =>1
                        ];

                        return response()->json($res,200);
                    }
                }
                $res = [
                    'msg' =>"User Not Found",
                    'error' =>1
                ];

                return response()->json($res,200);

            }else{
                $res = [
                    'msg' => "can't create new order",
                    'error' =>1
                ];
                return response()->json($res,200);

            }


        }else {

            $res = [
                'msg' => $validator->errors()->all()[0],
                'error' =>1
            ];

            return response()->json($res,200);
        }
    }

    /**
     *  Item end point
     * @return \Illuminate\Http\JsonResponse
     */
    public function item()
    {
        $rules = [
            "order_id" => "required|exists:orders,id",
            "product_id" => "required|exists:products,id",
            "status" => "required"
        ];

        $validator = \Validator::make(Input::all(), $rules);

        if ($validator->passes()) {

            $order_id   = (int) Input::get('order_id');
            $product_id = (int) Input::get('product_id');
            $status     = (int) Input::get('status');

            if(!$order = OrdersModel::find($order_id)){
                $res = [
                    'msg' => "Order Not found",
                    'error' => 1
                ];

                return response()->json($res, 200);
            }

            if ($order->checked == 1){
                $res = [
                    'msg' => "Sorry You must check In....",
                    'error' => 1
                ];

                return response()->json($res, 200);
            }

            $user  = Users::find($order->user_id);

            $item = ItemsModel::where('order_id','=', $order_id)
                ->where('product_id', '=', $product_id)
                ->first();

            if(!$item && $status == 1){
                if($product = ProductsModel::find($product_id)) {

                    $item = new ItemsModel();
                    $item->order_id     = $order_id;
                    $item->product_id   = $product_id;
                    $item->price        = $product->price;
                    $item->quantity     = 1;

                    $item->save();

                    if(strlen($token = $user->remember_token)) {
                        $data = new \stdClass();
                        $data->userToken = $token;
                        $data->body = 'Welcome To OTG Store......!';
                        $data->sound = 'default';
                        $data->dataArray = ['data' => ['data array']];

                        $firebase = new firebaseNotificationsServices();

                        $notified = $firebase->notifyUser($data);

                        /**
                         *  Notifications
                         */
                        $this->Notifier->notifyUser($data);

                        $res = [
                            'msg' => "Item Added",
                            "notified" => $notified,
                            'error' => 0
                        ];

                        return response()->json($res, 200);

                    }else{
                        $res = [
                            "msg" => "No firebase token",
                            "error" => 1
                        ];

                        return response()->json($res, 200);
                    }

                } else {
                    $res = [
                        "msg" => "Product Not Found",
                        "error" => 1
                    ];

                    return response()->json($res, 200);
                }

            } else if($item){
                $item->order_id = $order_id;
                $item->product_id = $product_id;

                $item->quantity = $item->quantity + $status;
                if( $item->quantity <= 0){
                    $item->delete();
                    //$item->save();

                    if(strlen($token = $user->remember_token)) {
                        $data = new \stdClass();
                        $data->userToken = $token;
                        $data->body = 'Product Removed';
                        $data->sound = 'default';
                        $data->dataArray = ['data' => ['data array']];

                        $firebase = new firebaseNotificationsServices();

                        $notified = $firebase->notifyUser($data);

                        /**
                         *  Notifications
                         */
                        $this->Notifier->notifyUser($data);

                        $res = [
                            'msg' => "Item Removed",
                            "notified" => $notified,
                            'error' => 0
                        ];

                        return response()->json($res, 200);

                    }else{
                        $res = [
                            "msg" => "No firebase token",
                            "error" => 1
                        ];

                        return response()->json($res, 200);
                    }


                }

                $item->save();

                if(strlen($token = $user->remember_token)) {
                    $data = new \stdClass();
                    $data->userToken = $token;
                    $data->body = 'Product Removed';
                    $data->sound = 'default';
                    $data->dataArray = ['data' => ['data array']];

                    $firebase = new firebaseNotificationsServices();

                    $notified = $firebase->notifyUser($data);

                    /**
                     *  Notifications
                     */
                    $this->Notifier->notifyUser($data);

                    $res = [
                        'msg' => "Quantity Updated",
                        "notified" => $notified,
                        'error' => 0
                    ];

                    return response()->json($res, 200);

                }else{
                    $res = [
                        "msg" => "No firebase token",
                        "error" => 1
                    ];

                    return response()->json($res, 200);
                }

            } else {

                $res = [
                    "msg" => "There is no Items of this product to remove",
                    "error" =>1
                ];

                return response()->json($res, 200);
            }


        } else {

            $res = [
                "msg" => $validator->errors()->all()[0],
                "error" =>1
            ];

            return response()->json($res, 200);
        }

    }


    public function out(){

        $rules = [
            'order_id' => "required|exists:orders,id"
        ];

        $validator = \Validator::make(Input::all(),$rules);

        if($validator->passes()){

            $order_id = (int) Input::get('order_id');

            $order = OrdersModel::find($order_id);

            if($order->checked){
                $res = [
                    "msg" => "This Order Has Been Check Out before.",
                    "error" => 1
                ];

                return response()->json($res,200);
            }
            $user = Users::find($order->user_id);

            $total = \DB::select("SELECT SUM(quantity*price) as total FROM items WHERE order_id = $order_id")[0]->total;

//            die(var_dump($total));
            if(($user->balance - $total) >= 0){

                $user->balance = $user->balance - $total;
                $user->save();


                $order->checked = 1;
                $order->save();

                $res = [
                    "msg" => "Checked Out Successfully",
                    "error" => 0
                ];

                return response()->json($res,200);

            } else {

                $res = [
                    "msg" => "There No Enough Credits, Recharge Please",
                    "error" => 1
                ];

                return response()->json($res,200);
            }

        }else{

            $res = [
                "msg" => $validator->errors()->first(),
                "error" => 1
            ];

            return response()->json($res,200);
        }

    }


}