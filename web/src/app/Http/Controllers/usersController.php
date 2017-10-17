<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\User;
use Illuminate\Support\Facades\Hash;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Facades\JWTAuth;

class usersController extends Controller
{
    public function get(){
        return Hash::make("maxxi");
        return "TEST.GET";

    }
    public function post(Request $request){

        $this->validate($request,[
           'email' => 'required|email',
            'password' => 'required'
        ]);

        $credentials = $request->only('email','password');

        try{
            if(! $token = JWTAuth::attempt($credentials)){
                return response()->json(['msg' => "Invalid Auth"],401);
            }
        } catch (JWTException $e){
            return response()->json(['msg' => "Could not create token"],500);
        }

        return response()->json(['token'=>$token]);
    }
}
