<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Project extends Model
{
    use HasFactory;

    function projectContractors(){
        return $this->hasMany(Contractorprojects::class);
    }
    function admin(){
        return $this->belongsTo(User::class);
    }
}
