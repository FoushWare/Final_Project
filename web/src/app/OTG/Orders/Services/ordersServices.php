<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 2/20/2018
 * Time: 4:13 AM
 */

namespace App\OTG\Orders\Services;


use App\OTG\Orders\Repositories\ordersRepositories;
use App\OTG\Services;
use Illuminate\Support\Facades\Input;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Facades\JWTAuth;

class ordersServices extends Services
{
    private $OrdersRepositories;


    public function __construct(){
        $this->OrdersRepositories = new ordersRepositories();
    }

    /**
     * receive the firebase token
     * @return \Illuminate\Http\JsonResponse|\Psr\Http\Message\StreamInterface
     */
    public function saveToken(){
        try {

            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }

            $rules = [
                "firebase_token" => "required"
            ];

            $validator = \Validator::make(Input::all(), $rules);
            if($validator->passes()){

                // Save USer Firebase Token
                $check = $this->OrdersRepositories->SaveFireBaseToken($user->id,Input::get('firebase_token'));
                if(!$check)
                    return response()->json(['msg' => "Query Exception","error"=>'1'], 500);


                //HTTP Request for the QR generator cloud
                $client = new \GuzzleHttp\Client();

                $method = 'POST'; //Method of the form

                // Form Options
                $options = [
                    'form_params' => [ // form parameters
                        'qrtext' => Input::get('firebase_token')
                    ]
                ];

                return QRCode::text('QR Code Generator for Laravel!')->svg();

            } else{ // Validation error
                $error = $validator->errors()->all()[0];
                return response()->json(['msg' => $error,"error"=>'1'], 500);
            }
        } catch (JWTException $e) {// Exception
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }
    }


}