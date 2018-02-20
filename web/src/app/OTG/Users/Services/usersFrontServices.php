<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 2/20/2018
 * Time: 11:19 AM
 */

namespace App\OTG\Users\Services;


use App\OTG\Services;
use App\OTG\Users\Repositories\usersRepositories;
use Illuminate\Database\QueryException;
use Illuminate\Support\Facades\Input;

class usersFrontServices extends Services
{
    private $usersRepositories;

    /**
     * usersFrontServices constructor.
     */
    public function __construct(){
        $this->usersRepositories = new usersRepositories();
    }

    /** Sign In function
     * @return bool
     */
    public function SignIn(){
        $rules = [
            'password'  => "required",
            'email'     => "required"
        ];

        $validator = \Validator::make(Input::all(),$rules);
        if(!$validator->passes()) {
            $this->setErrors($validator->errors()->all());
            return false;
        } else {
            $credentials = [
                'password'  => (Input::get('password')),
                'email'     => (Input::get('email'))
            ];
            try {
                $check = \Auth::attempt($credentials);
                if($check)
                    return true;
                $this->setErrors(["AUTH Error"]);
                return false;
            } catch (QueryException $e){
                $this->setErrors(["Auth Exception"]);
                return false;
            }
        }
    }

    /** Users Sign Up
     * @return bool
     */
    public function signup(){

        //set Rules
        $rules = [
            'name'  =>'required|min:1|max:50',
            'email' => 'required|email|unique:users',
            'password' => 'required| min:8|regex:/^.*(?=.{3,})(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\d\X])(?=.*[!$#%]).*$/',
            'confirmation'  =>"required|same:password",
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
                return  true;
            $this->setErrors(['Query Exception']);
            return  false;

        } else {// Validation Failed
            $this->setErrors($validator->errors()->all());
            return  false;
        }
    }
}