<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\OTG\Users\Services\apiUsersServices;


class testController extends Controller
{

    public function post(request $request){
        $x = new apiUsersServices();
        return $x->example($request);
    }

    /**
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function get(request $request){

    }
}
