<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 1/29/2018
 * Time: 2:16 PM
 */

namespace App\OTG;


use League\Flysystem\Exception;

class Services
{
    private $errors = [];

    /**
     * @param mixed $errors
     */
    public function setErrors($errors)
    {
        try {
            $this->errors = array_merge($this->errors, $errors);
            return true;
        } catch (Exception $exception){
            die("Services ERRORS Merging Exception");
//            return false;
        }
    }

    /**
     * @return mixed
     */
    public function getErrors()
    {
        return $this->errors;
    }


}