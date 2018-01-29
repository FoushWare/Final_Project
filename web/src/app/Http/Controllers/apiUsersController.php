<?php

namespace App\Http\Controllers;

use App\Cards;
use App\User;
use App\UsersCards;
use App\Http\Controllers\firebaseNotification;

use GuzzleHttp\Client;
use Illuminate\Contracts\Validation\Validator;
use Illuminate\Database\QueryException;
use Illuminate\Http\Request;
use App\Http\Requests;
use Illuminate\Support\Facades\Hash;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Facades\JWTAuth;

class apiUsersController extends Controller{

    public function __construct(){
        $this->middleware('jwt.auth',['only'=>[
            'addCredits'
        ]]);
    }

    public function signin(Request $request){

       
        if(!isset($request->email)){
            return  response()->json(['msg' => "Email Required",'error'=>'1'], 401);

        }
        if(!isset($request->password)){
            return  response()->json(['msg' => "password Required",'error'=>'1'], 401);

        }

        $this->validate($request, [
            'email' => 'required|email',
            'password' => 'required'
        ]);

//        $credentials = $request->only('email', 'password');
        $credentials = ["email"=>$request->email,"password"=>$request->password];
        try {
            if (!$token = JWTAuth::attempt($credentials)) {
                return response()->json(['msg' => "Invalid Auth","error"=>'1'], 401);
            }
        } catch (JWTException $e) {
            return response()->json(['msg' => "Could not create token","error"=>'1'], 500);
        }

        $user =  User::where('email', $request->email);
        return response()->json(['token' => $token,"msg"=>"Successfully SignIn","error"=>'0'],200);
        }

    public function signup(Request $request){

        if(!isset($request->email)){
            return  response()->json(['msg' => "Email Required",'error'=>'1'], 401);

        }
        if(!isset($request->password)){
            return  response()->json(['msg' => "password Required",'error'=>'1'], 401);

        }
        if(!isset($request->name)){
            return  response()->json(['msg' => "name Required",'error'=>'1'], 401);

        }

        if(!isset($request->phone)){
            return  response()->json(['msg' => "Phone Required",'error'=>'1'], 401);

        }

        if(!filter_var($request->email,FILTER_VALIDATE_EMAIL)){
            return  response()->json(['msg' => "Email Is Bad",'error'=>'1'], 401);
        }

        $u = User::where('email','=',$request->email)->first();

        if( $u && strtoupper( $u->email ) == strtoupper($request->email)){
            return  response()->json(['msg' => "Email has been used before",'error'=>'1'], 401);
        }

        $uppercase = preg_match('@[A-Z]@', $request->password);
        $lowercase = preg_match('@[a-z]@', $request->password);
        $number    = preg_match('@[0-9]@', $request->password);

        if(!$uppercase) {
            return  response()->json(['msg' => "Password Must Have at least capital litter",'error'=>'1'], 401);
        }


        if(!$lowercase) {
            return  response()->json(['msg' => "Password Must Have at least small litter",'error'=>'1'], 401);
        }

        if(!$number) {
            return  response()->json(['msg' => "Password Must Have at least one number",'error'=>'1'], 401);
        }


        if(strlen($request->password) < 8) {
            return  response()->json(['msg' => "Password Must be more than 7 letters",'error'=>'1'], 401);
        }


        if(strlen($request->phone)< 10){
            return  response()->json(['msg' => "Error In The Phone Number",'error'=>'1'], 401);
        }

        if(strlen($request->phone)> 15){
            return  response()->json(['msg' => "Error In The Phone Number",'error'=>'1'], 401);
        }

        $user = new User();
        $user->name = $request->name;
        $user->email = $request->email;
        $user->password = Hash::make($request->password);
        $user->phone = $request->phone;
        $user->group_id = 1;
        $user->pics_uploaded = 1;
        $user->save();
        try{
            $user->save();
            return  response()->json(['msg' => "User created",'error'=>'0'], 201);
        }catch (QueryException $e){
            return  response()->json(['msg' => "Query Exception",'error'=>'1'], 401);
        }
    }

    public function addCredits(Request $request){

        $this->validate($request, [
            'hash' => 'required'
        ]);

        if(!$user = JWTAuth::parseToken()->authenticate()){
            return response()->json(['msg' => "User not found","error"=>'1'], 404);
        }
        $card = Cards::where('hash','=',$request->hash)->first();

        if(!$card || $card->fire == 1){
            return response()->json(['msg' => "Sorry This Card Can not be Used","error"=>'1'], 404);
        }

        $client = User::find($user->id);
        $client-> balance += $card->value;

        $used = new UsersCards();
        $used->user_id = $user->id;
        $used->card_id = $card->id;

        try{
            $card->fire = 1;

            $client->save();
            $card->save();
            $used->save();

            return  response()->json(['msg' => "Successfully",'credits'=>$client->balance,"error"=>'0'], 200);
        }catch (QueryException $e){
            return  response()->json(['msg' => "Query Exception"], 401);
        }

    }

    public function profile(Request $request){
        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }

            $client = User::find($user->id);
            return response()->json(['msg' => "User Found",'data'=>$client,"error"=>'0'], 200);

        } catch (JWTException $e) {
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }
    }

    public function update(Request $request){
        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }

            $client = User::find($user->id);

            if(!isset($request->email)){
                return  response()->json(['msg' => "Email Required",'error'=>'1'], 401);

            }

            if(!isset($request->name)){
                return  response()->json(['msg' => "name Required",'error'=>'1'], 401);

            }

            if(!isset($request->phone)){
                return  response()->json(['msg' => "Phone Required",'error'=>'1'], 401);

            }

            if(!filter_var($request->email,FILTER_VALIDATE_EMAIL)){
                return  response()->json(['msg' => "Email Is Bad",'error'=>'1'], 401);
            }

            $u = User::where('email','=',$request->email)->first();

            if( $u && strtoupper( $u->email ) == strtoupper($request->email)){
                return  response()->json(['msg' => "Email has been used before",'error'=>'1'], 401);
            }

            if(strlen($request->phone)< 10){
                return  response()->json(['msg' => "Error In The Phone Number",'error'=>'1'], 401);
            }

            if(strlen($request->phone)> 15){
                return  response()->json(['msg' => "Error In The Phone Number",'error'=>'1'], 401);
            }

            $client->name = $request->name;
            $client->email = $request->email;
            $client->phone = $request->phone;
            $client->save();

            return response()->json(['msg' => "User Found",'data'=>$client,"error"=>'0'], 200);

        } catch (JWTException $e) {
            return response()->json(['msg' => "Could not create token","error"=>'1'], 500);
        }
    }

    /**
     *  users histories as an array of orders
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function  history(Request $request){
        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }

            //$client = User::find($user->id);
            $item = [
                "name" => "test item",
                "price" => "15$",
                "quantity" => 5
            ];

            $order1 = [
                'id' => "1",
                "date" => "Nov 15, 2017 11:59:00 pm",
                "items" => [$item,$item,$item]
            ];

            $order2 = [
                'id' => "2",
                "date" => "Nov 15, 2017 11:59:59 pm",
                "items" => [$item,$item]
            ];

            $orders = [
                0 => $order1,
                1 => $order2
            ];

            return response()->json(['msg' => "User Found",'history'=>$orders,"error"=>'0'], 200);

        } catch (JWTException $e) {
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }
    }
}