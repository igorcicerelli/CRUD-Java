import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Limitador extends PlainDocument {
	private static final long serialVersionUID = 1L;
	
	int tam = 0;
    String regex = "";

public Limitador(int a, String r) {
	this.tam = a;
	this.regex = r;
}
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
			int tamanho = (this.getLength() + str.length());
			if(tamanho <=tam) {
			super.insertString(offs, str.replaceAll(regex,""), a);
			}else
			{
				super.insertString(offs, str.replaceAll("[aA0-zZ9]",""), a);
			}
		}
	}  