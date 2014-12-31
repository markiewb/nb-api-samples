package com.mycompany.showerrorsoffile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.tools.Diagnostic;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.Task;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Foo",
        id = "com.foo.AreThereErrorsAction"
)
@ActionRegistration(
        displayName = "#CTL_AreThereErrorsAction"
)
@ActionReference(path = "Editors/text/x-java/Popup")
@Messages("CTL_AreThereErrorsAction=Are There Errors?")
public final class AreThereErrorsAction implements ActionListener {

    private final DataObject context;

    public AreThereErrorsAction(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        JavaSource js = JavaSource.forFileObject(context.getPrimaryFile());
        if (null == js) {
            return;
        }
        try {
            js.runUserActionTask(new Task<CompilationController>() {

                @Override
                public void run(CompilationController cc) throws Exception {
                    //cc.toPhase(JavaSource.Phase.PARSED) ???
                    StringBuilder s = new StringBuilder();
                    for (Diagnostic diagnostic : cc.getDiagnostics()) {
                        if (javax.tools.Diagnostic.Kind.ERROR.equals(diagnostic.getKind())) {
                            s.append("diagnostic = " + diagnostic + "\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, s.toString());
                }
            }, true);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
}
