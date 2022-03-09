<?php


namespace App\Security;
use HWI\Bundle\OAuthBundle\Security\Core\User\EntityUserProvider;
use App\Entity\User;
use HWI\Bundle\OAuthBundle\OAuth\Response\UserResponseInterface;
use Doctrine\ORM\EntityManagerInterface;
use HWI\Bundle\OAuthBundle\Connect\AccountConnectorInterface;
use Symfony\Component\Security\Core\User\UserInterface;



class MyEntityUserProvider extends EntityUserProvider implements AccountConnectorInterface {
    
    public function loaduserByOAuthUserResponse(UserResponseInterface $response)
    {
        $resourceOwnerName = $response->getResourceOwner()->getName ();
       if (!isset($this->properties[$resourceOwnerName])) {
           throw new \RuntimeException (sprintf( "No property defined for entity for resource owner '%s',",$resourceOwnerName));
       }
       $serviceName = $response->getResourceOwner()->getName();
        $setterId = 'set'. ucfirst($serviceName) . 'ID';
        $setterAccessToken = 'set'. ucfirst($serviceName) . 'AccessToken';

       // unique integer
        $username=$response->getUsername();
       if (null === $user = $this->finduser (array ($this->properties[$resourceOwnerName] => $username)))
          {
           // TODO: Create the user
          
              $user = new User ();
              $user->setEmail($response->getEmail());
              $user->setterId($username);
             $user->setterAccessToken($response->getAccessToken());
             $user->setRoles(["ROLE_CLIENT"]);
             $user->setPassword("test");
             $user->setEtat(1);
             $em =$this->getDoctrine()->getManager();
             $em->persist($user);
             $em->flush();
             return $user;

            }
            $user->setFacebookAccessToken($response->getAccessToken());
            return $user;

    }


    public function connect(UserInterface $user, UserResponseInterface $response)
    {
        if (!$user instanceof User) {
            throw new UnsupportedUserException(sprintf('Expected an instance of App\Model\User, but got "%s".', get_class($user)));
        }
        
        $property = $this->getProperty($response);
        $username = $response->getUsername();

        if (null !== $previousUser = $this->registry->getRepository(User::class)->findOneBy(array($property => $username))) {
            // 'disconnect' previously connected users
            $this->disconnect($previousUser, $response);
        }


        $serviceName = $response->getResourceOwner()->getName();
        $setter = 'set'. ucfirst($serviceName) . 'AccessToken';

        $user->$setter($response->getAccessToken());

        $this->updateUser($user, $response);
    }

    
     /**
     
     * Gets the property for the response.
     *
     * @param UserResponseInterface $response
     *
     * @return string
     *
     * @throws \RuntimeException
     */
    protected function getProperty(UserResponseInterface $response)
    {
        $resourceOwnerName = $response->getResourceOwner()->getName();

        if (!isset($this->properties[$resourceOwnerName])) {
            throw new \RuntimeException(sprintf("No property defined for entity for resource owner '%s'.", $resourceOwnerName));
        }

        return $this->properties[$resourceOwnerName];
    }
    /**
     * Disconnects a user.
     *
     * @param UserInterface $user
     * @param UserResponseInterface $response
     * @throws \TypeError
     */
    public function disconnect(UserInterface $user, UserResponseInterface $response)
    {
        $property = $this->getProperty($response);
        $accessor = PropertyAccess::createPropertyAccessor();

        $accessor->setValue($user, $property, null);

        $this->updateUser($user, $response);
    }


}
