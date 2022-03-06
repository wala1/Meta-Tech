<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Participants;
use App\Repository\ParticipantsRepository;
use App\Repository\CalendarRepository ; 


class ParticipantsController extends AbstractController
{
    /**
     * @Route("/participants", name="participants")
     */
    public function index(): Response
    {
        return $this->render('participants/index.html.twig', [
            'controller_name' => 'ParticipantsController',
        ]);
    }

 
      /**
     * @Route("participantsEvents", name="participants_events")
     */ 
    public function  participantsEvents(ParticipantsRepository $participants,CalendarRepository $calendars) {
        return $this->render("participants/participantsEvents.html.twig", [
            'participants' => $participants->findAll(),
            'calendars' => $calendars->findAll()
        ]);
     
    }
     /**
     * @Route("participantsToEvent/{id}", name="participants_to_event")
     */ 
    public function  participantsToEvent(ParticipantsRepository $participants,$id) {
        return $this->render("participants/participantsToEvent.html.twig", [
            'participants' => $participants->findByEvent($id)
        ]);
     
    }

  





}
