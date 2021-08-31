package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecicaltyService specicaltyService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService,
                      PetTypeService petTypeService, SpecicaltyService specicaltyService,
                      VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specicaltyService = specicaltyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int countPetType = petTypeService.findAll().size();

        if (countPetType == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);
        System.out.println("Dog type and Cat type Loaded ...");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specicaltyService.save(radiology);

        Speciality surgery = new Speciality();
        radiology.setDescription("Surgery");
        Speciality savedSurgery = specicaltyService.save(surgery);
        System.out.println("Radiology and Surgery speciality Loaded ...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Jackson");
        owner1.setAddress("80 Kim Giang");
        owner1.setCity("Ha noi");
        owner1.setTelephone("123123123123");

        Pet miPet = new Pet();
        miPet.setPetType(savedDogType);
        miPet.setOwner(owner1);
        miPet.setBirthDate(LocalDate.now());
        miPet.setName("lulu");
        owner1.getPets().add(miPet);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Peter");
        owner2.setLastName("Packer");
        owner2.setAddress("80 Kim Ma");
        owner2.setCity("Ha noi");
        owner2.setTelephone("234234234");

        Pet pePet = new Pet();
        pePet.setPetType(savedCatType);
        pePet.setOwner(owner2);
        pePet.setBirthDate(LocalDate.now());
        pePet.setName("meo meo");
        owner2.getPets().add(pePet);

        ownerService.save(owner2);
        System.out.println("Owner Loaded ...");

        Visit catVisit = new Visit();
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");
        catVisit.setPet(pePet);
        visitService.save(catVisit);
        System.out.println("Visit Loaded");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);
        vetService.save(vet2);
        System.out.println("Vet Loaded ...");
    }
}
