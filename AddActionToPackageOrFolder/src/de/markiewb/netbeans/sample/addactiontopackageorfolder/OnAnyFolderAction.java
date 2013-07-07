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
        id = "com.foo.bar.OnAnyFolderAction")
@ActionRegistration(
        displayName = "#CTL_OnAnyFolderAction")
@ActionReferences({
    @ActionReference(path = "Loaders/folder/any/Actions", position = 0)
})
@Messages("CTL_OnAnyFolderAction=ActionOnAnyFolder")
public final class OnAnyFolderAction implements ActionListener {

    private final DataFolder context;

    public OnAnyFolderAction(DataFolder context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, context.toString());
    }
}
