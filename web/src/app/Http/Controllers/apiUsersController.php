<?php

namespace App\Http\Controllers;

use App\OTG\Users\Models\Users;
use Illuminate\Database\QueryException;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use App\OTG\Users\Services\apiUsersServices;

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

    /** update user inf
     * @return \Illuminate\Http\JsonResponse
     */
    public function update(){
        return $this->usersServices->update();
    }

    /**
     * Client Orders History
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function  history(Request $request){
        return $this->usersServices->history();
    }
}