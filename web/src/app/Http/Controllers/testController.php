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
        $token = "edhuJVGnpKw:APA91bHTIggFofRE-6h3umqWb_Kv5fEvtyp4l4HrYtbr6HuDm5H7wLT-KL81EAxbiiYNyrYEyLhgjNVDUu7dni0s1iybKdA9JNQR4i2e_vR6PCFReW3YNe71Uggp5txYFJxUT7DWi6Qo";
        $data = new \stdClass();
        $data->userToken    = $token;
        $data->body         = 'Catch me if you can!';
        $data->sound        = 'default';
        $data->dataArray        = ['key' => 'value'];
        $firebase = new firebaseNotificationsServices();
        return [$firebase->notifyUser($data)];
    }

}
