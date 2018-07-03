<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::group(['prefix'=>'api/v1/users'],function (){
    // API
    Route::post('signin',   'Users\apiUsersController@signin');
    Route::post('signup',   'Users\apiUsersController@signup');
    Route::post('credits',  'Users\apiUsersController@addCredits');
    Route::post('update',   'Users\apiUsersController@update');
    Route::post('password', 'Users\apiUsersController@passwordUpdate');

    Route::get('profile',   'Users\apiUsersController@profile');
    Route::get('history',   'Users\apiUsersController@history');

    Route::get('logout',    'Users\apiUsersController@logout');
});

Route::group(['prefix'=>'api/v1/orders'],function () {
// orders
//    Route::post('/registration', 'Orders\ordersController@saveToken');
//
    Route::post('registration', function ()
    {
        $x = new App\Http\Controllers\Orders\ordersController();
        $order_id = $x->saveToken();
        if(is_int($order_id)) {

            return QrCode::color(89, 48, 1)->size(300)->generate($order_id);

        }
        return $order_id;


    });

    Route::get('/{id}', 'Orders\ordersController@getOrderItems');


});

Route::get('qr-code', function ()
{
    return QrCode::size(200)->generate('Make me into a QrCode!');
});

/**
 * Ras end Points
 */
Route::group(['prefix'=>'/ras'],function () {
// orders
    Route::post('in',       'Raspberry\raspberryController@checkIn');
    Route::post('user',     'Raspberry\raspberryController@user');
    Route::post('item',     'Raspberry\raspberryController@item');
    Route::post('out',      'Raspberry\raspberryController@checkOut');

    Route::get('user/{id}',      'Raspberry\raspberryController@read');
    Route::post('user/{id}',      'Raspberry\raspberryController@set');


});




Route::group(['prefix' => '','middleware' => 'web'],function (){
    //public Routes
    Route::get('/', function () {
        return view('welcome');
    });

    Route::get('signup',    'Front\publicController@getSignUp');
    Route::post('signup',   'Front\publicController@postSignUp');

    Route::get('signin',    'Front\publicController@getSignIn');
    Route::post('signin',   'Front\publicController@postSignIn');


    Route::get('forgot',    'Front\publicController@getForgot');
    Route::post('forgot',   'Front\publicController@postForgot');

    Route::get('/reset',function (){
        return view('public.reset');
    });

    Route::get('QRLogin',function (){
        return "<h1>Coming Soon</h1>";
    });
});

//TEST
Route::get('test',function (){
    //return Hash::make("123");
    return " dfjkgndfjgn".phpversion();
});

Route::post('test','testController@post');
