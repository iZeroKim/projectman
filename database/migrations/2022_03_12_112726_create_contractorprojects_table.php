<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateContractorprojectsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('contractorprojects', function (Blueprint $table) {
            $table->id("contractorproject_id");
            $table->unsignedBigInteger("project_id");
            $table->unsignedBigInteger("contractor_id");
            $table->timestamp('created_at')->default(now());
            $table->timestamp('updated_at')->nullable();
            $table->foreign('project_id')->references('project_id')->on('projects');
            $table->foreign('contractor_id')->references('contractor_id')->on('contractors');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('contractorprojects');
    }
}
