<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CalendarRepository ; 
use Symfony\Component\HttpFoundation\Request;
use App\Repository\ParticipantsRepository;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use App\Repository\PubBackRepository;

use App\Form\CalendarType ;
use App\Entity\Calendar;
use App\Entity\User;
use App\Entity\Coupon;
use App\Entity\PubBack;

use App\Repository\CouponRepository;


use App\Entity\Participants;

use Symfony\Component\Form\Extension\Core\Type\SubmitType;



class CalendarController extends AbstractController
{

    ##################"Pour traiter la liste des events dans une calendrier#################""




    /**
     * @Route("/Calendar", name="calendar")
     */
    public function index(CalendarRepository $calendar): Response
    {
        $events=$calendar->findAll();
        $rdvs= [];
        foreach($events as $event)
        {
            $rdvs[] =[
                'id' => $event->getId(),
                'title' => $event->getTitle(),
                'description' => $event->getDescription(),
                'start' => $event->getStart()->format('Y-m-d H:i:s'),
                'end' => $event->getEnd()->format('Y-m-d H:i:s'),
                'allDay' => $event->getAllDay(),
                 'backgroundColor' => $event->getBackgroundColor(),
                'borderColor' => $event->getBorderColor(),
                'textColor' => $event->getTextColor(),
      ];
        }
        $data =json_encode($rdvs);
        return $this->render('calendar/index.html.twig', compact('data'));
    }












#########################""""Pour traiter la liste des events dans un tableau ############################"""




  /**
     * @Route("/Events", name="calendar_index", methods={"GET"})
     */
    public function AllEvents(CalendarRepository $calendarRepository ,ParticipantsRepository $participants): Response
    {
        return $this->render('calendar/listEvents.html.twig', [
            'calendars' => $calendarRepository->findAll(),
            'participants' => $participants->findAll()
        ]);
    }
 /**
     * @Route("/newEvent", name="calendar_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $calendar = new Calendar();
        $form = $this->createForm(CalendarType::class, $calendar);
        $form->add('Add Event', SubmitType::class) ; 

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $calendar->setOrigine("admin");
            $calendar->setEtat(1);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($calendar);
            $entityManager->flush();

            return $this->redirectToRoute('calendar_index');
        }

        return $this->render('calendar/newEvent.html.twig', [
            'calendar' => $calendar,
            'formEvent' => $form->createView(),
        ]);
    }



 /**
     * @Route("/editEvent/{id}", name="edit_event")
     */
    public function update(Request $request,$id )
    {
        $calendar= $this->getDoctrine()->getRepository(Calendar::class)->find($id) ; 

        $form = $this->createForm(CalendarType::class,$calendar) ; 
        $form->add('save changes', SubmitType::class) ; 

      
        $form->handleRequest($request) ;
         
        if ($form->isSubmitted()  && $form->isValid() ) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($calendar);
            $em->flush() ; 
            return $this->redirectToRoute('calendar_index') ; 

        } 

        return $this->render("calendar/newEvent.html.twig", [
            'calendar' => $calendar,
            'formEvent' => $form->createView()
        ]) ;
    }
   /**
     * @Route("cancelEvent/{id}", name="cancel_event")
     */ 

    public function cancelEvent(Calendar $calendar,\Swift_Mailer $mailer ){
       
        // $participants= $this->getDoctrine()->getRepository(Participants::class)->findByEvent($id) ; 
        // $user= $this->getDoctrine()->getRepository(User::class)->findAll() ; 

      
         $message= (new \Swift_Message('Event'))
         ->setFrom('aalimi.wala@gmail.com')
         ->setTo('wala.alimi@esprit.tn')
         ->setBody(
             $this->renderView(
          'emails\canceledEvent.html.twig' 
         ) ,
          'text/html'

          ) ;  
          $mailer->send($message);
        //   removeParticipant(Participants $participant) 
        
         $em =$this->getDoctrine()->getManager();
         $em->remove($calendar);
         $em->flush();
         return $this->redirectToRoute('calendar_index');
        }
 

 
################## deuxième table requests ############################""""

  /**
     * @Route("/Requests", name="requests_sent", methods={"GET"})
     */
    public function requests(CalendarRepository $calendarRepository ): Response
    {
        return $this->render('calendar/requestEvents.html.twig', [
            'calendars' => $calendarRepository->findAll(),
        ]);
    }

/**
 * @Route("Requests/accept/{id}", name="accept_event")

 */
public function acceptEvent(Calendar $calendar)
{
        $calendar->setEtat(1);
         $em =$this->getDoctrine()->getManager();
         $em->persist($calendar);
        $em->flush();
    return $this->redirectToRoute('requests_sent');
}

/**
 * @Route("Requests/refuse/{id}", name="refuse_event")

 */
public function RefuseEvent(Calendar $calendar)
{  
     $calendar->setEtat(-1);
    $em =$this->getDoctrine()->getManager();
    $em->persist($calendar);
   $em->flush();
    return $this->redirectToRoute('requests_sent');
}









        ################################# affcihage dans front##############################""""
/**
     * @Route("/eventFront", name="event_front")
     */
    public function eventFront(): Response
    {
        $repo = $this->getDoctrine()->getRepository(Calendar::class) ; 
        $calendar = $repo->findAll() ; 
          
        $repo1 = $this->getDoctrine()->getRepository(PubBack::class) ; 
        $pubBack = $repo1->findAll() ;

        return $this->render('calendar/eventFront1.html.twig', [
            'calendar' => $calendar,
            'pubBack' => $pubBack
        ]);
    }

    /**
     * @Route("/event_detail/{id}", name="event_detail")
     */
    public function eventDetail($id,ParticipantsRepository $participants): Response
    {
        $repo = $this->getDoctrine()->getRepository(Calendar::class) ; 
        $calendar = $repo->findById($id) ; 
        $repo1 = $this->getDoctrine()->getRepository(PubBack::class) ; 
        $pubBack = $repo1->findAll() ;

      
        return $this->render('calendar/eventShow.html.twig', [
            'pubBack'=>$pubBack,
            'calendar' => $calendar,
            'participants' => $participants->findAll()

          
        ]);
    }





      ############################Front Calendar for user ############################"""

    /**
     * @Route("/CalendarUser", name="calendar_user")
     */
    public function calendarUser(CalendarRepository $calendar): Response
    {     
         $repo = $this->getDoctrine()->getRepository(PubBack::class) ; 
         $pubBack = $repo->findAll() ;
        $events=$calendar->findAll();
        $rdvs= [];
        foreach($events as $event)
        {
            $rdvs[] =[
                'id' => $event->getId(),
                'title' => $event->getTitle(),
                'description' => $event->getDescription(),
                'start' => $event->getStart()->format('Y-m-d H:i:s'),
                'end' => $event->getEnd()->format('Y-m-d H:i:s'),
                'allDay' => $event->getAllDay(),
                 'backgroundColor' => $event->getBackgroundColor(),
                'borderColor' => $event->getBorderColor(),
                'textColor' => $event->getTextColor(),
      ];
        }
        $data =json_encode($rdvs);
        return $this->render('calendar/calendarFront.html.twig', compact('data'));
    }


   /**
     * @Route("/newEventRequest", name="calendar_new_request", methods={"GET","POST"})
     */
    public function newEventRequest(Request $request): Response
    {
        $calendar = new Calendar();
        $form = $this->createForm(CalendarType::class, $calendar);
        $form->add('send Request', SubmitType::class) ; 

        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $calendar->setOrigine("client");
            $calendar->setEtat(0);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($calendar);
            $entityManager->flush();
            $this->addFlash('success', 'Request is sent to admin! wait for his approval to be published!');

           return $this->redirectToRoute('event_front');
        }

        return $this->render('calendar/newEventClient.html.twig', [
            'calendar' => $calendar,
            'formEvent' => $form->createView(),
        ]);
    } 
  
     /**
     * @Route("/participate/{id}", name="participate", methods={"GET","POST"})
     */
    public function participate(Request $request , $id,Calendar $calendar)
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        // $c = new Coupon ;
        // $c->setCodeCoup($user->getUsername());
        // $c->setNum($id);
        // $em = $this->getDoctrine()->getManager();
        // $em = $this->getDoctrine()->getManager() ; 
        // $em->persist($c);
        // $em->flush() ; 
        // return $this->redirectToRoute('event_front');
        $p=new  Participants;
        $p->setEvent($calendar);
        $p->setUser($user);
        $em = $this->getDoctrine()->getManager();
        $em->persist($p);
        $em->flush() ; 
        return $this->redirectToRoute('events_user');

    }

 ##########################JSON#################################




  /**
     * @Route("eventsJson", name="liste_events_json")
     */ 
    public function  listEventsJson(NormalizerInterface $Normalizer) {
        $repo=$this->getDoctrine()->getRepository(Calendar::class);
        $calendar=$repo->findAll();
       
      
        $jsonContent=$Normalizer->normalize($calendar,'json',['groups'=>'post:read']);
         return new Response (json_encode($jsonContent));
    
     
     
         } 
     /**
      
     * @Route("addEventJson/new", name="add_event_json" ,methods = {"GET", "POST"})
     */
    

    public function  addEventJson(Request $request,NormalizerInterface $Normalizer) {

        $em=$this->getDoctrine()->getManager();
        $calendar=new Calendar();
        $calendar->setTitle($request->get('title'));
        $calendar->setStart($request->get('start'));
        $calendar->setEnd($request->get('end'));
        $calendar->setDescription($request->get('description'));
        $calendar->setAllDay($request->get('allDay'));


 
        $em->persist($calendar);
        $em->flush();
        $jsonContent=$Normalizer->normalize($calendar,'json',['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
 
          } 

          /**
    * @Route("deleteEventJson/{id}", name="deleteEventJson" ,methods = {"GET", "POST"})
    */
   

   public function deleteEventJson(Request $request,NormalizerInterface $Normalizer,$id) {

    $em=$this->getDoctrine()->getManager();
    $calendar=$em->getRepository(Calendar::class)->find($id);
    $em->remove($calendar);
    $em->flush();
    $jsonContent=$Normalizer->normalize($calendar,'json',['groups' => 'post:read']);
    return new Response("Event Deleted successfuly".json_encode($jsonContent));

   }











}

