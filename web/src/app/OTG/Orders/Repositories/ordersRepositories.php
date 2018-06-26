<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 2/20/2018
 * Time: 4:21 AM
 */

namespace App\OTG\Orders\Repositories;


use App\OTG\Firebase\Services\firebaseNotificationsServices;
use App\OTG\Repositories;
use Illuminate\Database\QueryException;
use App\OTG\Users\Models\Users;


class ordersRepositories extends Repositories {

    /** save the firebase token of the user
     * @param $uid
     * @param $firebaseToken
     * @return bool
     */
    public function SaveFireBaseToken($uid, $firebaseToken){

        $client = Users::find($uid);
        $client->remember_token = $firebaseToken;
        try {
            return $this->checkFireBaseToken($firebaseToken) && $client->save();
        } catch (QueryException $e){
            return false;
        }
    }


    public function sendNotification($firebaseToken){

        $firebase = new firebaseNotificationsServices();

        $data = new \stdClass();

        $data->userToken    = $firebaseToken;
        $data->body         = 'Open Current Order...!';
        $data->sound        = 'default';
        $data->dataArray        = ['key' => 'value'];


        return $firebase->notifyUser($data);
    }




}