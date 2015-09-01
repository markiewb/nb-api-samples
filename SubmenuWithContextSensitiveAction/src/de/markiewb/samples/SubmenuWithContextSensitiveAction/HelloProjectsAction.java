package de.markiewb.samples.SubmenuWithContextSensitiveAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "MyActions/SubActions",
        id = "de.markiewb.netbeans.sample.HelloProjectsAction"
)
@ActionRegistration(
        displayName = "#CTL_HelloProjectsAction"
)
@Messages("CTL_HelloProjectsAction=HelloProjects...")
public final class HelloProjectsAction implements ActionListener {

    private final List<Project> context;

    public HelloProjectsAction(List<Project> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, context.size() + " projects selected: " + context);
    }
}
