<?php

namespace App\Http\Controllers\Orders;

use App\Http\Controllers\Controller;
use App\OTG\Orders\Services\ordersServices;

class ordersController extends Controller
{
    private $ordersServices;

    /**
     * ordersController constructor.
     */
    public function __construct(){
        $this->middleware('jwt.auth',['only'=>[
            'addCredits'
        ]]);

        $this->ordersServices = new ordersServices();
    }

    public function newOrder(){
        return $this->ordersServices->createNewOrder();
    }


    public function currentOrders(){
        //TODO:: users orders
    }
}
