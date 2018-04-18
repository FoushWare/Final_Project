<?php

namespace App\Http\Controllers;

use App\OTG\Firebase\Services\firebaseNotificationsServices;
use App\User;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\OTG\Users\Services\apiUsersServices;


class testController extends Controller
{

    public function post(request $request)
    {

    }


    public function get(request $request)
    {
        $token = "kyomxsW6HUYqG2FNns9Rkv2YckC2";

        $data = new \stdClass();
        $data->userToken    = $token;
        $data->body         = 'Catch me if you can!';
        $data->sound        = 'default';
        $data->dataArray        = ['key' => 'value'];

        $firebase = new firebaseNotificationsServices();
        return [$firebase->message()];
    }


}
