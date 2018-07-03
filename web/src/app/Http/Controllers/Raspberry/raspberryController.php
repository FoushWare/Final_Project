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
use App\OTG\Users\Models\Users;

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


    /**
     * @param $id
     * @return \Illuminate\Http\JsonResponse
     */
    public function read($id){
        if(!$user = Users::find((int)$id))
            return response()->json(["msg" =>"USER NOT FOUND","error"=>1],200);



        return response()->json(["msg" =>"User Pics info","pics"=>$user->pics_uploaded,"error"=>0],200);


    }

    /**
     * @param $id
     * @return \Illuminate\Http\JsonResponse
     */
    public function set($id){
        if(!$user = Users::find((int)$id))
            return response()->json(["msg" =>"USER NOT FOUND","error"=>1],200);

        $user->pics_uploaded = 1;

        $user->save();

        return response()->json(["msg" =>"User Pics Updated","error"=>0],200);


    }
}