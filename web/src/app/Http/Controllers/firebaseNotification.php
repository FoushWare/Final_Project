<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 11/18/2017
 * Time: 3:11 PM
 */

namespace app\Http\Controllers;


class firebaseNotification
{
    private $token;

    public  function send_notification ($tokens, $message)
    {
        $url = 'https://fcm.googleapis.com/fcm/send';
        $fields = array(
            'registration_ids' => $tokens,
            'data' => $message
        );

        $headers = array(
            'Authorization:key=AAAAzd-ETkA:APA91bHVcxjUlDT59HMKpaWNVGgYRISuaeqfibxB4W1D0Gjeydvec_xuiOYzRjTwO1d9DperFWqhXGylwxIdKwwuaCbBBq6e6ChvAKE7dLS_57Sq4u06ugmTq1CD3YIfUN_9pfSamlaT',
            'Content-Type: application/json'
        );

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        $result = curl_exec($ch);
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
        curl_close($ch);
        return $result;
    }



}