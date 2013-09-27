package utilitarios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

//Classe TextFile programada por Raimundo Machado Costa
public class TextFile {
	private TextFile2 arq;
	private String nomeArq;

	public TextFile(String nomeArq) {
		this.nomeArq = nomeArq;
	}

	public boolean reset() {
		try {
			arq = new TextFile2(TextFile2.INPUT, nomeArq);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public void rewrite() {
		try {
			arq = new TextFile2(TextFile2.OUTPUT, nomeArq);
		}
		catch (Exception e) {

		}
	}

	public void closeFile() {
		arq.close();
	}

	public boolean eof() {
		return arq.eof();
	}

	public int readInt() {
		int n = arq.readInt();
		arq.skipWhiteSpace();
		return n;
	}
	
	public void writeln(String s){
		arq.println(s);
	}

	public double readDouble() {
		double x = arq.readDouble();
		arq.skipWhiteSpace();
		return x;
	}

	public float readfloat() {
		float x = arq.readFloat();
		arq.skipWhiteSpace();
		return x;
	}

	public String readString(int tamanho) {
		String s = arq.readLine(tamanho);
		arq.skipWhiteSpace();
		return s;
	}

	public String readString() {
		return arq.readLine();
	}
	
	public char readChar(){
		char c = arq.readChar();
		arq.skipWhiteSpace();
		return c;
	}
	
	public void SkipWhiteSpace(){
		arq.skipWhiteSpace();
	}
	
	private class TextFile2 {

		public static final boolean INPUT = true;

		public static final boolean OUTPUT = false;

		private FileObject fileObj;

		public TextFile2(boolean mode, String fileName) throws FileNotFoundException {
			if (mode == INPUT)
				fileObj = new InputFileObject(fileName);
			else
				fileObj = new OutputFileObject(fileName);
		} // end constructor

		public char readChar() {
			return fileObj.readChar();
		} // end readChar

		public String readLine() {
			return fileObj.readLine();
		} // end readLine

		public String readLine(int tamanho) {
			return fileObj.readLine(tamanho);
		} // end readLine

		public String readWord() {
			return fileObj.readWord();
		} // end readWord

		public int readInt() {
			return fileObj.readInt();
		} // end readInt

		public double readDouble() {
			return fileObj.readDouble();
		} // end readDouble

		// Raimundo
		public float readFloat() {
			return fileObj.readFloat();
		}

		public void skipWhiteSpace() {
			fileObj.skipWhiteSpace();
		} // end skipWhiteSpace

		public boolean eof() {
			return fileObj.eof();
		} // end eof

		public void println(String s) {
			fileObj.println(s);
		} // end print

		public void close() {
			fileObj.close();
		} // end close

		public void abort(String msg) {
			System.out.println(msg);
			System.exit(1);
		} // end abort

		private abstract class FileObject {
			// the name of the file
			protected String fileName;

			public abstract String readLine();

			public abstract String readLine(int tamanho);

			public abstract String readWord();

			public abstract void skipWhiteSpace();

			public abstract char readChar();

			public abstract int readInt();

			public abstract double readDouble();

			public abstract float readFloat();

			public abstract void println(String s);

			public abstract void print(String s);

			public abstract void close();

			public abstract boolean eof();
		} // end class FileObject

		private class InputFileObject extends FileObject {
			// The reader that does the real work.
			private BufferedReader reader;

			private StringBuffer buffer = new StringBuffer();

			// Value returned by private function to denote end of file.
			private static final int EOF_VALUE = -1;

			// True if this file is the standard input (so that we won't
			// try to close it!)
			private boolean standardInput = false;

			public InputFileObject(String fileName) throws FileNotFoundException {
				this.fileName = fileName;
				try {
					reader = new BufferedReader(new FileReader(fileName));
				}
				catch (FileNotFoundException e) {
					throw new FileNotFoundException();
					// abort("Error: input file \"" + fileName + "\" does not exist");
				}
			} // end constructor

			// Three specific abort methods with frequently-used error messages
			private void errorAbort() {
				abort("I/O error while reading from file \"" + fileName + "\"");
			} // end errorAbort

			private void eofAbort() {
				abort("attempt to read past end of input file \"" + fileName + "\"");
			} // end eofAbort

			private void writeAbort() {
				abort("error: attempt to write to input file \"" + fileName + "\"");
			} // end writeAbort

			// Reads the next character from the input file and returns it as an
			// int. If we're at the end of the file, returns -1 instead.
			private int readCharOrEOF() {
				try {
					if (buffer == null) { // already hit EOF
						return EOF_VALUE;
					}
					else
						if (buffer.length() == 0) {
							String newLine = reader.readLine();
							if (newLine == null)
								return EOF_VALUE;
							else {
								buffer.append(newLine);
								buffer.append('\n');
							} // end if
						} // end if

					// If we get here, the buffer is not null or length 0, so return
					// the next character
					char result = buffer.charAt(0);
					buffer.deleteCharAt(0);
					return result;
				} // end try
				catch (IOException e) {
					errorAbort();
					return ' '; // keep compiler happy
				} // end catch
			} // end readCharOrEOF

			// Pushes a character back onto the input stream. Used when we
			// read a character we're not ready to send to the user.
			private void pushChar(char ch) {
				if (buffer == null)
					buffer = new StringBuffer(ch);
				else
					buffer.insert(0, ch);
			} // end pushChar

			// Returns true if ch is a "whitespace" character
			private boolean isWhite(char ch) {
				switch (ch) {
					case ' ':
					case '\t':
					case '\n':
						return true;
					default:
						return false;
				} // end switch
			} // end isWhite

			public boolean eof() {
				int nextChar = readCharOrEOF();
				if (nextChar == EOF_VALUE)
					return true;
				else {
					pushChar((char) nextChar);
					return false;
				} // end if
			} // end eof

			public void skipWhiteSpace() {
				char ch = ' ';
				while (true) {
					if (eof()) {
						buffer = null;
						return;
					}
					else
						if (!isWhite(ch)) {
							pushChar(ch);
							return;
						}
						else
							// we just read a whitespace character, so keep going
							ch = readChar();
				} // end while
			} // end skipWhiteSpace

			public String readWord() {
				// skip white space, then read and accumulate characters
				// until non white space character or eof
				StringBuffer word = new StringBuffer();
				skipWhiteSpace();
				if (eof())
					eofAbort();
				while (true) {
					if (eof())
						return word.toString();
					char ch = readChar();
					if (isWhite(ch)) {
						pushChar(ch);
						return word.toString();
					}
					else {
						word.append(ch);
					} // end if
				} // end while
			} // end readWord

			public String readLine() {
				if (eof())
					eofAbort();
				// read and accumulate characters until end of line or end of file
				StringBuffer newLine = new StringBuffer();
				int newChar = readCharOrEOF();
				while (newChar != '\n' && newChar != EOF_VALUE) {
					newLine.append((char) newChar);
					newChar = readCharOrEOF();
				} // end while
				return newLine.toString();
			} // end readLine

			// Raimundo
			public String readLine(int tamanho) {
				if (eof())
					eofAbort();
				// read and accumulate characters until end of line or end of file
				StringBuffer newLine = new StringBuffer();
				int newChar = readCharOrEOF();
				int i = 1;
				while (newChar != '\n' && newChar != EOF_VALUE) {
					newLine.append((char) newChar);
					if (i == tamanho)
						break;
					newChar = readCharOrEOF();
					i++;
				} // end while
				return newLine.toString();
			} // end readLine

			public char readChar() {
				int ch = readCharOrEOF();
				if (ch == EOF_VALUE)
					eofAbort();
				return (char) ch;
			} // end readChar

			public int readInt() {
				String intWord = readWord();
				try {
					return Integer.parseInt(intWord);
				} // end try
				catch (NumberFormatException e) {
					abort("error: \"" + intWord + "\" is not a legal integer");
					return 0; // keep compiler happy
				} // end catch
			} // end readInt

			public double readDouble() {
				String doubleWord = readWord();
				try {
					return Double.parseDouble(doubleWord);
				} // end try
				catch (NumberFormatException e) {
					abort("error: \"" + doubleWord + "\" is not a legal double");
					return 0; // keep compiler happy
				} // end catch
			} // end readDouble

			// Raimundo
			public float readFloat() {
				String floatWord = readWord();
				try {
					return Float.parseFloat(floatWord);
				} // end try
				catch (NumberFormatException e) {
					abort("error: \"" + floatWord + "\" is not a legal float");
					return 0; // keep compiler happy
				} // end catch
			} // end readDouble

			public void close() {
				if (standardInput)
					return; // don't close the standard input
				try {
					reader.close();
				}
				catch (IOException e) {
					abort("Error while attempting to close file \"" + fileName + "\"");
				}
				fileName = "closed file";
			} // end close

			// Calling an output method for an object of this class is an error.
			public void println(String s) {
				writeAbort();
			} // end println

			public void print(String s) {
				writeAbort();
			} // end print

		} // end class InputFileObject

		/***************************************************************************
		 * An OutputFileObject implements an output file, using the PrintStream
		 * class.
		 **************************************************************************/
		private class OutputFileObject extends FileObject {
			// the writer that does the real work
			private PrintStream writer;

			public OutputFileObject(String fileName) {
				this.fileName = fileName;
				try {
					writer = new PrintStream(new FileOutputStream(fileName));
				}
				catch (FileNotFoundException e) {
					abort("Error: can't open file \"" + fileName + "\" for writing");
				}
			} // end constructor

			// abort with a common error message
			private void readAbort() {
				abort("Error: attempt to read from output file \"" + fileName + "\"");
			} // end readAbort

			public void println(String s) {
				print(s);
				println();
			} // end println

			public void println() {
				writer.println();
			} // end println

			public void print(String s) {
				writer.print(s);
			} // end print

			public void close() {
				if (writer != System.out) {
					writer.close();
					fileName = "closed file";
				} // end if
			} // end close

			/* ***********************************************************************
			 * Calling an input method for an object of this class is an error.
			 * *********************************************************************
			 */
			public String readLine() {
				readAbort();
				return null; // keep compiler happy
			} // end readLine

			public int readInt() {
				readAbort();
				return 0; // keep compiler happy
			} // end readInt

			public double readDouble() {
				readAbort();
				return 0; // keep compiler happy
			} // end readInt

			public boolean eof() {
				readAbort();
				return false; // keep compiler happy
			} // end eof

			public char readChar() {
				readAbort();
				return ' '; // keep compiler happy
			} // end readChar

			public String readWord() {
				readAbort();
				return null; // keep compiler happy
			} // end readChar

			public void skipWhiteSpace() {
				readAbort();
			} // end skipWhiteSpace

			@Override
			public String readLine(int tamanho) {
				readAbort();
				return null; // keep compiler happy
			}

			@Override
			public float readFloat() {
				readAbort();
				return 0; // keep compiler happy
			}

		} // end class OutputFileObject

	} // end class TextFile


}
