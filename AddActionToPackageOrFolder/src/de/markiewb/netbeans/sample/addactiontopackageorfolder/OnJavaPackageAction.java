package de.markiewb.netbeans.sample.addactiontopackageorfolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataFolder;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "com.foo.bar.OnJavaPackageAction")
@ActionRegistration(
        displayName = "#CTL_OnJavaPackageAction")
@ActionReferences({
    @ActionReference(path = "Projects/package/Actions", position = 0)
})
@Messages("CTL_OnJavaPackageAction=OnJavaPackageAction")
public final class OnJavaPackageAction implements ActionListener {

    private final DataFolder context;

    public OnJavaPackageAction(DataFolder context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        //Projects/package/Actions
        JOptionPane.showMessageDialog(null, context.toString());
    }
}
