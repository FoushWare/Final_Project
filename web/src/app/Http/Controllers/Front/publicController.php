<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 2/20/2018
 * Time: 1:09 PM
 */

namespace app\Http\Controllers\Front;

use App\Http\Controllers\Controller;
use App\OTG\Users\Services\usersFrontServices;

class publicController extends Controller
{
    private $Front;

    /**
     * publicController constructor.
     */
    public function __construct()
    {
        $this->Front = new usersFrontServices();
    }

    public function getSignUp (){
        return view('public.signUp');
    }

    public function postSignUp(){
        $check = $this->Front->signup();
        if($check){
            return view('public.signIn')->with('successMessage', "Congratulation, You have been registered.");
        }
        return redirect()->back()->with('errors',$this->Front->getErrors())->withInput();
    }

    public function getSignIn(){
        if(\Auth::check())
            return redirect('/users');
        return view('public.signIn');
    }

    public function postSignIn(){
        $check = $this->Front->SignIn();
        if($check){
            return redirect('/users');
        }
        return redirect()->back()->with('errors',$this->Front->getErrors())->withInput();
    }


    public function postForgot(){
        $check = $this->Front->forget();
        if($check) {
            return view('public.forgot')->with('successMessage', "Please, Check your email inbox, we have sent a rest link");
        }
        return redirect()->back()->with('errors',$this->Front->getErrors())->withInput();
    }

    public function getForgot(){
        return view('public.forgot');
    }

}
