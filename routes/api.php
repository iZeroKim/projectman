<?php

use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('/user', function (Request $request) {
    return $request->user();
});


Route::get('/users', function(){
    return User::all();
});

//Projects
Route::get('projects', 'ProjectController@index');
Route::get('projects/{id}', 'ProjectController@show');
Route::post('projects', 'ProjectController@store');
Route::put('projects/{id}', 'ProjectController@update');
Route::delete('projects/{id}', 'ProjectController@destroy');

//Administrators
Route::post('register', 'Auth\RegisterController@register');
Route::post('login', 'Auth\LoginController@login');

//Contractors
Route::get('contractors', 'ContractorController@index');
Route::get('contractors/{id}', 'ContractorController@show');
Route::post('contractors', 'ContractorController@store');
Route::put('contractors/{id}', 'ContractorController@update');
Route::delete('contractor/{id}', 'ContractorController@destroy');

