<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 4/20/2018
 * Time: 11:30 AM
 */

namespace App\OTG\Raspberries\Services;


use App\OTG\Services;

class raspberriesServices extends Services
{
    private $ordersServices;

    public function __construct()
    {
        $this->ordersServices = new OrdersSercies();
    }

}