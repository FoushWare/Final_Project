<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

use App\User;
use App\UsersCards;
use App\Http\Controllers\firebaseNotification;

use Illuminate\Contracts\Validation\Validator;
use Illuminate\Database\QueryException;
use Illuminate\Support\Facades\Hash;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Facades\JWTAuth;

class ordersController extends Controller
{
    public function __construct(){
        $this->middleware('jwt.auth',['only'=>[
            'addCredits'
        ]]);
    }

    /** send me firebase token of new shopping order the
     * receive QR of your current Order
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse|mixed
     */
    public function newOrder(Request $request){
        try {

            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }

            if(!isset($request->firebase_token)){
                return  response()->json(['msg' => "Firebase token Required",'error'=>'1'], 401);

            }


            $curl = curl_init();
            curl_setopt_array($curl, array(
                CURLOPT_URL => "http://wallidsamy.pythonanywhere.com/qrcode/",
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 30,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "POST",
                CURLOPT_POSTFIELDS => "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"qrtext\"\r\n\r\n".$request->firebase_token."\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--",
                CURLOPT_HTTPHEADER => array(
                    "cache-control: no-cache",
                    "content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW",
                    "postman-token: 371419d2-6726-e2a2-c29b-6eeea310e729"
                ),
            ));
            $response = curl_exec($curl);
            $err = curl_error($curl);

            curl_close($curl);

            if ($err) {
                return response()->json(['msg' => "Invalid Token","error"=>'1'], 200);
            } else {

                $client = User::find($user->id);
                $client->remember_token = $request->firebase_token;
                $client->save();

                return $response;
            }

        } catch (JWTException $e) {
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }
    }


    public function currentOrders(){
        //TODO:: users orders
    }
}
