<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\OTG\Users\Services\apiUsersServices;


class testController extends Controller
{

    public function post(request $request)
    {

    }

    /**
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function get(request $request)
    {
        $errors = [1,2];

        array_push($errors,[4]);

        die(var_dump($errors));
    }
}
