<?php



namespace App\Security ;
// namespace Symfony\Component\Security\Core\Exception;

use App\Entity\User as AppUser;

use Symfony\Component\Security\Core\User\UserCheckerInterface;
use Symfony\Component\Security\Core\Exception\CustomUserMessageAuthenticationException;

use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Core\Exception;
use Symfony\Component\Security\Core\Exception\CustomUserMessageAccountStatusException;
 

class UserChecker implements UserCheckerInterface
 {
public function checkPreAuth(UserInterface $user)
{
    if(!$user instanceof AppUser){
        return;
    }
   if ( $user->getEtat() ==1 )
   {
    //throw new CustomUserMessageAccountStatusException("Your account is not activated yet , check your email to activate your account");

     throw new CustomUserMessageAuthenticationException("Your account is not activated yet , check your email to activate your account");
   //throw new \Exception("Your account is blocked");
  
   }
   if ( $user->getActivationToken()!=NULL)
   {
   // throw new CustomUserMessageAccountStatusException("you did not activate your account yet");

   throw new CustomUserMessageAuthenticationException("you did not activate your account yet");
      // throw new \Exception("Your account is not activated yet , check your email to activate your account");

 }


}
public function checkPostAuth(UserInterface $user)
{
    if(!$user instanceof User){
        return;
    }
}







 }