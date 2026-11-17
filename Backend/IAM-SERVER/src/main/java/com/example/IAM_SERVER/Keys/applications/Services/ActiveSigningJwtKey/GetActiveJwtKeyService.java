package com.example.IAM_SERVER.Keys.applications.Services.ActiveSigningJwtKey;

import com.example.IAM_SERVER.Keys.adapters.out.Jpa.JpaRepository.KeyjpaRepository;
import com.example.IAM_SERVER.Keys.domain.model.ActiveKey;
import com.example.IAM_SERVER.Keys.domain.ports.out.GetJwtKeys;
import com.example.IAM_SERVER.Keys.domain.ports.out.LoadJwtKeys;

import com.example.IAM_SERVER.Keys.domain.ports.out.SaveJwtKey;
import org.springframework.stereotype.Service;

@Service
public class GetActiveJwtKeyService implements GetJwtKeys {

    final private KeyjpaRepository repository;
    final private LoadJwtKeys loader ;
    final private SaveJwtKey saver;

       public GetActiveJwtKeyService(KeyjpaRepository repo , LoadJwtKeys  loader, SaveJwtKey saver){
           this.loader = loader;
           this.saver =  saver ;
           this.repository = repo ;

       }

@Override
public ActiveKey getJwtkeys(){
           if(repository.existsAnyKey()){
               System.out.println("hey i m going to laod the jwtkey from db");
               return loader.loadkey();
           } else{
               System.out.println("jwt doesnt exist in db so generating");
               return saver.saveJwtKey();
           }
       }
}
