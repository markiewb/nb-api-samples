package de.markiewb.netbeans.sample.editablediff;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import javax.swing.SwingUtilities;
import org.netbeans.api.diff.DiffController;
import org.netbeans.api.diff.Difference;
import org.netbeans.api.diff.StreamSource;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;
import org.openide.windows.TopComponent;

@ActionID(category = "Edit", id = "de.markiewb.netbeans.sample.editablediff.EditableDiffAction")
@ActionRegistration(displayName = "#CTL_DiffAction")
@ActionReferences({
    @ActionReference(path = "Editors/Popup")})
@Messages("CTL_DiffAction=Editable diff...")
public final class EditableDiffAction implements ActionListener {

    private final FileObject file;

    public EditableDiffAction(FileObject context) {
	this.file = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
	final String originalText = getOriginalText(file);
	final StreamSource remote = StreamSource.createSource("name2", "original", file.getMIMEType(), new StringReader(originalText));

	final StreamSource local = EditableStreamSource.createEditableSource("name1", "current", file.getMIMEType(), file);

	openDiffWindow(file, local, remote, "Diff of " + file.getNameExt() + " to original");
    }

    public void openDiffWindow(final FileObject localFile, final StreamSource local, final StreamSource remote, final String title) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    final DiffController diffController = DiffController.createEnhanced(remote, local);

		    final TopComponent tc = new TopComponent();
		    tc.setDisplayName(title);

		    tc.setLayout(new BorderLayout());
		    makeDiffWindowSaveable(tc, localFile);
		    tc.add(diffController.getJComponent(), BorderLayout.CENTER);
		    tc.open();
		    tc.requestActive();
		} catch (IOException ex) {
		}
	    }
	});
    }

    /**
     * Put the node of dataObject of the fileObject into "globallookup". This
     * allows saving via CTRL-S shortkey from within the editable diff TC. See
     * http://netbeans.org/bugzilla/show_bug.cgi?id=223703
     *
     * @param tc
     * @param fileObject
     */
    private void makeDiffWindowSaveable(TopComponent tc, FileObject fileObject) {
	if (tc != null) {
	    Node node;
	    try {
		final DataObject dobj = DataObject.find(fileObject);
		node = dobj.getNodeDelegate();
	    } catch (DataObjectNotFoundException e) {
		node = new AbstractNode(Children.LEAF, Lookups.singleton(fileObject));
	    }
	    tc.setActivatedNodes(new Node[]{node});
	}
    }

    public String getOriginalText(FileObject file) {
	// TODO this is only a mockup
	// TODO get original text from other sources like SCM, DB, template files..
	try {
	    return file.asText("UTF-8").replace("public ", "public final ");
	} catch (IOException ex) {
	    Exceptions.printStackTrace(ex);
	}
	return "";
    }

    public static class EditableStreamSource extends StreamSource {

	private String name;
	private String title;
	private String mimeType;
	private FileObject fileObject;

	private EditableStreamSource(String name, String title, String mimeType, FileObject fileObject) {
	    this.name = name;
	    this.title = title;
	    this.mimeType = mimeType;
	    this.fileObject = fileObject;
	}

	public static StreamSource createEditableSource(String name, String title, String mimeType, FileObject fileObject) {
	    return new EditableStreamSource(name, title, mimeType, fileObject);
	}

	@Override
	public String getName() {
	    return this.name;
	}

	@Override
	public String getTitle() {
	    return this.title;
	}

	@Override
	public Lookup getLookup() {
	    return Lookups.fixed(fileObject);
	}

	@Override
	public boolean isEditable() {
	    return fileObject.canWrite();
	}

	@Override
	public String getMIMEType() {
	    return mimeType;
	}

	@Override
	public Reader createReader() throws IOException {
	    return new FileReader(FileUtil.toFile(fileObject));
	}

	@Override
	public Writer createWriter(Difference[] conflicts) throws IOException {
	    return null;
	}
    }
}
