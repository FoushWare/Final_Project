<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 1/29/2018
 * Time: 11:53 AM
 */

namespace App\OTG\Users\Services;

use App\OTG\Services;
use Illuminate\Support\Facades\Input;

use App\OTG\Users\Repositories\usersRepositories;
use Tymon\JWTAuth\Facades\JWTAuth;
use Tymon\JWTAuth\Exceptions\JWTException;

class apiUsersServices extends Services
{
    protected $usersRepositories;

    public function __construct()
    {
        $this->usersRepositories = new usersRepositories();
    }

    /**
     * @return \Illuminate\Http\JsonResponse
     */
    public function signin(){
        // Rules
        $rules = array(
            'email' => "required|email",
            'password' => 'required'
        );

        $validator = \Validator::make(Input::all(),$rules);

        if($validator->passes()){
            $email = Input::get('email');
            $password = Input::get('password');

            //Auth layer
            return $this->Auth($email,$password);
        }
        else {
            return response()->json(["msg"=> $validator->errors()->all()[0],"error"=>'1'],401);
        }
    }

    /** Sign Up new User
     * @return \Illuminate\Http\JsonResponse
     */
    public function signup(){

        //set Rules
        $rules = [
            'name'  =>'required|min:1|max:50',
            'email' => 'required|email|unique:users',
            'password' => 'required| min:8|regex:/^.*(?=.{3,})(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\d\X])(?=.*[!$#%]).*$/',
            'phone' =>  'required|min:10|numeric'
        ];

        //Validator creation
        $validator = \Validator::make(Input::all(),$rules);

        if($validator->passes()){ //check

            //Set User Info
            $user = new  \stdClass();
            $user->name = Input::get('name');
            $user->email = Input::get('email');
            $user->phone = Input::get('phone');
            $user->password = Input::get('password');

            //save new user
            $check = $this->usersRepositories->signUp($user);

            if($check)// check
                return  response()->json(['msg' => "User created",'error'=>'0'], 201);
            return  response()->json(['msg' => "Query Exception",'error'=>'0'], 401);

        } else {// Validation Failed
            $error = $validator->errors()->all()[0];
            return  response()->json(['msg' => $error,'error'=>'1'], 401);
        }
    }

    /**
     * Add Credits for user
     * @return \Illuminate\Http\JsonResponse
     */
    public function addCredits(){
        // check Auth and token
        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }

        } catch (JWTException $e) {
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }

        // set rules
        $rules = [
            'hash' => 'required'
        ];

        //Validation creation
        $validator = \Validator::make(Input::all(), $rules);

        // check validation
        if($validator->passes()){

            // database saving
            $check = $this->usersRepositories->addCredits($user, Input::get('hash'));

            // check
            if($check) {
                // saved
                return response()->json(['msg' => "Successfully", 'credits' => '', "error" => '0'], 200);
            } else {
                // not saved
                return  response()->json(['msg' => "Invalid Card",'error'=>'1'], 401);
            }

        } else {
            // Validation error
            $error = $validator->errors()->all()[0];
            return  response()->json(['msg' => $error,'error'=>'1'], 401);
        }
    }


    /**
     * Get user Profile
     * @return \Illuminate\Http\JsonResponse
     */
    public function profile(){
        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User Not found","error"=>'1'], 404);
            }
            //get profile
            $profile = $this->usersRepositories->profile($user);
            //check
            if(!$profile)
                return response()->json(['msg' => "User Not Found","error"=>'0'], 404);
            //profile
            return response()->json(['msg' => "User Found",'data'=>$profile,"error"=>'0'], 200);
            // Throw JWT Exception
        } catch (JWTException $e) {
            // Exception
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }

    }

    /** update basic info  of user
     * @return \Illuminate\Http\JsonResponse
     */
    public function update(){
        $rules = [
            'name'  =>'required|min:1|max:50',
            'email' => 'required|email',
            'phone' =>  'required|min:10|numeric'
        ];

        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }
        } catch (JWTException $e) { // Exception
            return response()->json(['msg' => "Exception token","error"=>'1'], 500);
        }

        $validator = \Validator::make(Input::all(),$rules);

        if($validator->passes()){
            //Set User Info
            $client = new  \stdClass();
            $client->name  = Input::get('name');
            $client->email = Input::get('email');
            $client->phone = Input::get('phone');

            // send data to database
            $check = $this->usersRepositories->update($user->id,$client);
            // Check  data saving
            if($check)
                return response()->json(['msg' => "Info Updated","error"=>'0'], 200);
            // Can't Save
            return response()->json(['msg' => "Can't Save New Info Now","error"=>'1'], 500);
        }

        // return error
        $error = $validator->errors()->all()[0];
        return response()->json(['msg' => $error,"error"=>'1'], 500);

    }

    /**
     * update the user password
     * @return \Illuminate\Http\JsonResponse
     */
    public function updatePassword(){
        $rules = [
            'password'  => 'required| min:8|regex:/^.*(?=.{3,})(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\d\X])(?=.*[!$#%]).*$/',
            'password_confirm' => 'required|same:password'
        ];

        try {
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }
        } catch (JWTException $e) { // Exception
            return response()->json(['msg' => "Exception token","error"=>'1'], 500);
        }

        $validator = \Validator::make(Input::all(),$rules);

        if($validator->passes()){
            //get password
            $password  = Input::get('password');

            // send data to database
            $check = $this->usersRepositories->updatePassword($user->id,$password);
            // Check  data saving
            if($check) {
                JWTAuth::invalidate(Input::get('token'));
                return response()->json(['msg' => "Password Updated", "error" => '0'], 200);
            }
            // Can't Save
            return response()->json(['msg' => "Can't Save New Info Now","error"=>'1'], 403);
        }
        // return error
        $error = $validator->errors()->all()[0];
        return response()->json(['msg' => $error,"error"=>'1'], 500);
    }

    /**
     * get client History
     * @return \Illuminate\Http\JsonResponse
     */
    public function  history(){
        try { // Validate Token
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }
        } catch (JWTException $e) { // Exception
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }

        // get the user Orders
        $orders = $this->usersRepositories->clientHistory();

        //submit the response with the orders history
        return response()->json(['msg' => "User Found",'history'=>$orders,"error"=>'0'], 200);
    }

    /**  simple Auth function
     * @param $email
     * @param $password
     * @return \Illuminate\Http\JsonResponse
     */
    public function Auth($email, $password){
        // Auth parts to check
        $credentials = [
            "email" => $email,
            "password" => $password
        ];
        try {
            if (!$token = JWTAuth::attempt($credentials)) { // try generate token
                return response()->json(['msg' => "Invalid Auth","error"=>'1'], 401);
            }
        } catch (JWTException $e) { // JWT Exception
            return response()->json(['msg' => "Could not create token","error"=>'1'], 500);
        }

        // Token generated and ready to be used.
        return response()->json(['token' => $token,"msg"=>"Successfully SignIn","error"=>'0'],200);

    }

    /** logout
     * Destroy the Token
     * @return \Illuminate\Http\JsonResponse
     */
    public function logout(){
        try { // Validate Token
            if(!$user = JWTAuth::parseToken()->authenticate()){
                return response()->json(['msg' => "User not found","error"=>'1'], 404);
            }
            if(JWTAuth::invalidate(Input::get('token')))
                return response()->json(["msg"=>"Successfully Logout","error"=>'0'],200);
        } catch (JWTException $e) { // Exception
            return response()->json(['msg' => "Invalid Token","error"=>'1'], 500);
        }



    }

}