<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CalendarRepository ; 
use Symfony\Component\HttpFoundation\Request;
use App\Form\CalendarType ;
use App\Entity\Calendar;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;



class CalendarController extends AbstractController
{

    //Pour traiter la liste des events dans une calendrier




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






























//Pour traiter la liste des events dans un tableau 




  /**
     * @Route("/Events", name="calendar_index", methods={"GET"})
     */
    public function AllEvents(CalendarRepository $calendarRepository): Response
    {
        return $this->render('calendar/listEvents.html.twig', [
            'calendars' => $calendarRepository->findAll(),
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
 










        // affcihage dans front
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



}

