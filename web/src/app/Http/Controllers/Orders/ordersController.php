<?php

namespace App\Http\Controllers\Orders;

use App\Http\Controllers\Controller;
use App\OTG\Orders\Models\ItemsModel;
use App\OTG\Orders\Services\ordersServices;

class ordersController extends Controller
{
    private $ordersServices;

    /**
     * ordersController constructor.
     */
    public function __construct(){
        $this->middleware('jwt.auth',['only'=>[
            'getOrderItems','saveToken'
        ]]);

        $this->ordersServices = new ordersServices();
    }

    /** Create New order
     * @return \Illuminate\Http\JsonResponse|\Psr\Http\Message\StreamInterface
     */
    public function saveToken(){
        return $this->ordersServices->saveToken();
    }


    public function getOrderItems($id){

        $items = \DB::table('items')
            ->leftJoin('products','items.product_id','=','products.id')
            ->select(['items.*','products.title','products.price'])
            ->where('order_id','=',$id)
            ->get();

        $res = [
            "msg" => "order info",
            "items" => $items,
            "error" => 0
        ];
        return response()->json($res, 200);

    }
}
