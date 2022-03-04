<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CalendarRepository ; 
use Symfony\Component\HttpFoundation\Request;
use App\Repository\ParticipantsRepository;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use App\Form\CalendarType ;
use App\Entity\Calendar;
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
     * @Route("deleteEvent/{id}", name="delete_event")
     */ 

    public function deleteEvent(Calendar $calendar){
        $em =$this->getDoctrine()->getManager();
         $em->remove($calendar);
         $em->flush();
         return $this->redirectToRoute('calendar_index');
        }
 










        ################################# affcihage dans front##############################""""
/**
     * @Route("/eventFront", name="event_front")
     */
    public function eventFront(): Response
    {
        $repo = $this->getDoctrine()->getRepository(Calendar::class) ; 
        $calendar = $repo->findAll() ; 

        return $this->render('calendar/eventFront1.html.twig', [
            'calendar' => $calendar
        ]);
    }

    /**
     * @Route("/event_detail/{id}", name="event_detail")
     */
    public function eventDetail($id,ParticipantsRepository $participants): Response
    {
        $repo = $this->getDoctrine()->getRepository(Calendar::class) ; 
        $calendar = $repo->findById($id) ; 

      
        return $this->render('calendar/eventShow.html.twig', [
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

