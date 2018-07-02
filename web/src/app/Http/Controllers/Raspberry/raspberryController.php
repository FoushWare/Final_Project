<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 4/20/2018
 * Time: 11:20 AM
 */

namespace App\Http\Controllers\Raspberry;


use App\Http\Controllers\Controller;
use App\OTG\Raspberries\Services\raspberriesServices;

class raspberryController extends Controller
{
    private $service;


    public function __construct(){

        $this->service = new raspberriesServices();
    }


    public function checkIn(){
        return $this->service->add();
    }

    public function item(){
        return $this->service->item();
    }

    public function checkOut(){
        return $this->service->out();
    }
}