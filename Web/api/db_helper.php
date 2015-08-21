<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of db_helper
 *
 * @author tz7
 */


class db_helper {
    //put your code here
    protected  static $instance;
    public function __construct() {
       
    }
    public static function getInstance(){
         if(!self::$instance){
            include "config.php";
            self::$instance = new PDO("mysql:host=$db_server;dbname=$db_database;charset=utf8", $db_user,$db_pass);
        } 
        return self::$instance;
    }
    
    public static function query($sql, $args = null){
        try{
            $instance = self::getInstance();
            $query = $instance->prepare($sql);
            $rst = $query->execute($args);
            if($rst){
                  $rst_set = $query->fetchAll();//PDO::FETCH_ASSOC
                  return $rst_set;
            }
            else{
                echo 'Query failed';
                return NULL;
            }
           
        }
        catch(Exception $e){
            echo $e->getMessage();
            return NULL;
        }
    }
    
}
