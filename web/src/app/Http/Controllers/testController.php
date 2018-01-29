<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use OTG\Users\Services\apiUsersServices;

class testController extends Controller
{

    public function post(request $request){

        die('<br>POST test');
    }

    /**
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function get(request $request){
        // TODO:: Fix me
        $x =  new apiUsersServices();
        $x->test();
        return response()->json([],200);
    }
}
