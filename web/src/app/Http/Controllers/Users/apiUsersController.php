<?php

namespace App\Http\Controllers\Users;

use App\OTG\Users\Services\apiUsersServices;
use App\Http\Controllers\Controller;

class apiUsersController extends Controller{

    protected $usersServices;

    public function __construct(){
        $this->usersServices = new apiUsersServices(); // to get accessed for any method
    }

    /**
     * sign in function
     * @return \Illuminate\Http\JsonResponse
     */
    public function signin(){
        return $this->usersServices->signin();
    }

    /**
     * Log out Function
     * @return array|\Illuminate\Http\JsonResponse
     */
    public function logout(){
        return $this->usersServices->logout();
    }

    /**
     * Sign Us new user
     * @return \Illuminate\Http\JsonResponse
     */
    public function signup(){
        return $this->usersServices->signup();
    }

    /**
     * Add Credits
     * @return \Illuminate\Http\JsonResponse
     */
    public function addCredits(){
        return $this->usersServices->addCredits();
    }

    /**
     * Profile
     * @return \Illuminate\Http\JsonResponse
     */
    public function profile(){
        return $this->usersServices->profile();
    }

    /**
     * Client Orders History
     * @return \Illuminate\Http\JsonResponse
     */
    public function  history(){
        return $this->usersServices->history();
    }


    /** update user inf
     * @return \Illuminate\Http\JsonResponse
     */
    public function update(){
        return $this->usersServices->update();
    }

    /**Update password
     * @return \Illuminate\Http\JsonResponse
     */
    public function passwordUpdate(){
        return $this->usersServices->updatePassword();
    }
}