package com.ly.repository;

import java.util.List;

public interface Repository <T> {

   public  void insert(T data);
   
   public List<T> lister(); 


}
