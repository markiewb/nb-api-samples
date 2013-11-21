package de.markiewb.netbeans.sample.addactiontopackageandjavafile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Build",
        id = "com.foo.bar.OnJavaFilesAndPackageAction")
@ActionRegistration(
        displayName = "#CTL_OnJavaFilesAndPackageAction")
@ActionReferences({
    @ActionReference(path = "Projects/package/Actions", position = 500),
    @ActionReference(path = "Loaders/text/x-java/Actions", position = 500)
})
@Messages("CTL_OnJavaFilesAndPackageAction=OnJavaFilesAndPackageAction")
public final class OnJavaFilesAndPackageAction implements ActionListener {

    private final List<DataObject> context;

    public OnJavaFilesAndPackageAction(List<DataObject> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        StringBuilder sb=new StringBuilder();
        sb.append("<html>");
        for (DataObject dataObject : context) {
            sb.append(dataObject.getPrimaryFile().isFolder()?"Package: ":"File: ");
            sb.append(dataObject.getPrimaryFile().getPath());
            sb.append("<br/>");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
