<?php
/**
 * Created by PhpStorm.
 * User: maxxi
 * Date: 1/29/2018
 * Time: 11:53 AM
 */

namespace App\OTG\Users\Services;
use App\Http\Controllers\Controller;
use App\OTG\Services;

class apiUsersServices extends Services
{
    public function example($request){

        $rules = array(
            'string'  =>  'email',
            'int'    =>  'int|max:5'
        );

        $validator = \Validator::make($request->all(),$rules);

        if($validator->passes()) {
            return "Validation passed";
        }
        return "Validation failed";
    }
}