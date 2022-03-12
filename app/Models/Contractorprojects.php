<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Contractorprojects extends Model
{
    use HasFactory;

    function contractors(){
        return $this->belongsTo(Contractor::class);
    }

    function projects(){
        return $this->belongsTo(Project::class);
    }
}
