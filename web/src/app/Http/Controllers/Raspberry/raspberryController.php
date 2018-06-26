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
    public function newOrder(){
        $orders = new raspberriesServices();
        return 0;
    }


}