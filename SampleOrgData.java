package FinalProject;

import java.util.List;

public final class SampleOrgData {
    public static Group createSampleOrg() {
        Worker scrooge = new Worker("Scrooge McDuck", "CEO");

        Worker gladstone = new Worker("Gladstone Gander", "Support Manager");
        Group customerSupport = new Group(
                "Customer Support",
                gladstone,
                List.of(
                        new Worker("Gyro Gearloose", "Support Engineer"),
                        new Worker("Magica De Spell", "Support Technician"),
                        new Worker("Launchpad McQuack", "Support Staff")
                )
        );

        Worker daisy = new Worker("Daisy Duck", "Development Lead");
        Group softwareDev = new Group(
                "Software Development",
                daisy,
                List.of(
                        new Worker("Huey Duck", "Developer"),
                        new Worker("Dewey Duck", "Developer"),
                        new Worker("Louie Duck", "Developer")
                )
        );

        Worker donald = new Worker("Donald Duck", "Marketing Lead");
        Group marketing = new Group(
                "Marketing",
                donald,
                List.of(new Worker("Gus Goose", "Marketing Specialist"))
        );

        return new Group(
                "Top management",
                scrooge,
                List.of(
                        new Worker("Grandma Duck", "secretary"),
                        marketing,
                        softwareDev,
                        customerSupport
                )
        );
    }
}