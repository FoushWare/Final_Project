<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 2/6/2018
 * Time: 2:40 PM
 */

namespace App\OTG\Users\Repositories;


use App\OTG\Repositories;
use App\OTG\Users\Models\Users;
use App\OTG\Cards\Models\Cards;
use App\OTG\Cards\Models\UsersCards;
use Illuminate\Database\QueryException;
use League\Flysystem\Exception;

class usersRepositories extends Repositories
{
    /** Sign In
     * @param string $email
     * @param string $password
     * @return \Illuminate\Http\JsonResponse
     */
    public function signIn($email="", $password=""){
        // TODO:: Sing In
    }

    /** Save new User
     * @param \stdClass $data
     * @return bool
     */
    public function signUp(\stdClass $data){

        // model object
        $user = new Users();

        // set the user data
        $user->name = $data->name;
        $user->email = $data->email;
        $user->password = \Hash::make($data->password);
        $user->phone = $data->phone;
        $user->group_id = 1;
        $user->pics_uploaded = 1;

        try{
            //save new user
            $user->save();
            return  true;
        }catch (QueryException $e){
            //Query Exception
            return false;
        }
    }

    /**
     * Add credits for client
     * @param $user
     * @param $hash
     * @return bool
     */
    public function addCredits($user, $hash){

        // get the card data
        $card = Cards::where('hash','=',$hash)->first();

        // fired 'used' cards check
        if(!$card || $card->fire == 1){
            return false;
        }

        // update client balance

        $client = Users::find($user->id);
        $client-> balance += $card->value;

        // relate the used cart to the client
        $used = new UsersCards();
        $used->user_id = $user->id;
        $used->card_id = $card->id;

        try{
            // Save 'Card', 'client', 'used'
            $card->fire = 1;
            $x =  $client->save();
            $y = $card->save();
            $z =  $used->save();
            // Saved
            return  $x && $y && $z;
        }catch (QueryException $e){
            //Query Exception
            return  false;
        }
    }

    /**
     * @param $user
     * @return User | bool
     */
    public function profile($user){
        $client = Users::find($user->id); // get
        return $client;
    }

    /** Update user info
     * @param $id
     * @param $user
     * @return bool
     */
    public function update($id,$user){
        if($client = Users::find($id)){
            // New data
            $client->name = $user->name;
            $client->email = $user->email;
            $client->phone = $user->phone;

            try{
                // Save
                return $client->save();
            }catch (QueryException $e){
                //Query Exception
                return  false;
            }
        }
        return false;



    }

    /** Reset Password
     * @param $id
     * @param $password
     * @return bool
     */
    public function updatePassword($id,$password){
        if($user = Users::find($id)){
            // New data
            $user->password  = \Hash::make($password);

            try{
                $user->save();
                return true;
            }catch (QueryException $e){
                //  Query Exception
                return  false;
            }
        }
        return false; // User not Found
    }

    public function clientHistory(){
        // TODO :: get user Orders

        return $this->testData();
    }

    public function testData(){
        $item = [
            "name" => "test item",
            "price" => "15$",
            "quantity" => 5
        ];

        $order1 = [
            'id' => "1",
            "date" => "Nov 15, 2017 11:59:00 pm",
            "items" => [$item,$item,$item]
        ];

        $order2 = [
            'id' => "2",
            "date" => "Nov 15, 2017 11:59:59 pm",
            "items" => [$item,$item]
        ];

        $orders = [
            0 => $order1,
            1 => $order2
        ];

        return $orders;
    }
}