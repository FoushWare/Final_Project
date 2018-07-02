<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 4/17/2018
 * Time: 12:18 PM
 */

namespace App\OTG\Firebase\Services;

use LaravelFCM\Message\OptionsBuilder;
use LaravelFCM\Message\PayloadDataBuilder;
use LaravelFCM\Message\PayloadNotificationBuilder;
use LaravelFCM\Message\Topics;
use LaravelFCM\Mocks\MockDownstreamResponse;

use FCM;


class firebaseNotificationsServices
{
    private $optionBuilder;
    private $dataBuilder;
    private $notificationBuilder;

    /**
     * crate a notification
     * firebaseNotificationsServices constructor.
     * @param string $title
     */
    public function __construct($title='')
    {
        $this->optionBuilder = new OptionsBuilder();
        $this->dataBuilder   = new PayloadDataBuilder();
        $this->notificationBuilder = new PayloadNotificationBuilder($title);
    }


    /** notift a user
     * @param \stdClass $data
     * @return bool
     * @throws \LaravelFCM\Message\InvalidOptionException
     */
    public function notifyUser(\stdClass $data){

        $userToken  = $data->userToken;
        $sound      = $data->sound;
        $body       = $data->body;
        $dataArray  = $data->dataArray;

        $this->optionBuilder->setTimeToLive(60*200);

        $this->notificationBuilder->setBody($body)->setSound($sound);

        $this->dataBuilder->addData($dataArray);

        $option = $this->optionBuilder->build();
        $notification = $this->notificationBuilder->build();
        $data = $this->dataBuilder->build();

        $downstreamResponse = FCM::sendTo($userToken, $option, $notification, $data);

        return $downstreamResponse->numberSuccess()? true :false;
    }


}