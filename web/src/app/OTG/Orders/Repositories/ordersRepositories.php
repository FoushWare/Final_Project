<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 2/20/2018
 * Time: 4:21 AM
 */

namespace App\OTG\Orders\Repositories;


use App\OTG\Repositories;
use Illuminate\Database\QueryException;
use App\OTG\Users\Models\Users;

class ordersRepositories extends Repositories {

    public function SaveFireBaseToken($uid, $firebaseToken){
        $client = Users::find($uid);
        $client->remember_token = $firebaseToken;
        try {
            return $client->save();
        } catch (QueryException $e){
            return false;
        }
    }

}