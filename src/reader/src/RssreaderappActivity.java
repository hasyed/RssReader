package reader.src;

//import android.R;
import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.widget.Toast;
import android.widget.ArrayAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import reader.src.td;

public class RssreaderappActivity extends Activity {
	private String check;
	private String check1;
	private int len;
	private ListView listview;
	private tdcustomAdapter ladapter;
	private String rsslink;
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button feed=(Button)findViewById(R.id.btn1);
		final EditText url=(EditText)findViewById(R.id.url);
		
		feed.setOnClickListener(new OnClickListener(){
			
			//@SuppressWarnings("unchecked")
			public void onClick(View v){
				rsslink=url.getText().toString();
				ArrayList<td> feedlistholder;
				feedlistholder=WordDefinition();
				listview=(ListView)findViewById(R.id.listView1);
				//ladapter=new ArrayAdapter<String>(RssreaderappActivity.this,R.layout.rowlayout,feedlistholder);
				ladapter=new tdcustomAdapter(RssreaderappActivity.this,R.layout.rowlayout,feedlistholder);
				listview.setAdapter(ladapter);
			}
			
		});
		
	}

	public ArrayList<td> WordDefinition() {
		InputStream inputStream = null;
		String strDefinition = "";
		check = "";
		String strDefinition1 = "";
		check1 = "";
		len = 0;
		ArrayList<td> feedlist=new ArrayList<td>();
		//ArrayList<String> feedlist=new ArrayList<String>();
		try {
			inputStream = OpenHttpConnection(rsslink);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			Document document = null;
			DocumentBuilder documentBuilder;
			try {
				documentBuilder = builderFactory.newDocumentBuilder();
				document = documentBuilder.parse(inputStream);

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			document.getDocumentElement().normalize();

			//listview=(ListView) findViewById(R.id.listView1);	
		    
		
			
			NodeList itemTags = document.getElementsByTagName("item");
			for (int i = 0; i < itemTags.getLength(); i++) {
				Node itemNode = itemTags.item(i);
				if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
					// ---convert the Node into an Element---
					Element item = (Element) itemNode;
					// ---get all the <title> elements under
					// the <item> element---
					NodeList titleTag = (item).getElementsByTagName("title");
					len = titleTag.getLength();

					strDefinition = "";
					Element title = (Element) titleTag.item(0);

					// ---get all the child nodes under the
					// <title> element---
					NodeList textNodes = ((Node) title).getChildNodes();

					// ---get the first node, which contains the text---
					strDefinition += ((Node) textNodes.item(0)).getNodeValue()
							+ ". ";

					// ---get all the <description> elements under
					// the <item> element---
					item = (Element) itemNode;
					NodeList descriptionTag = (item)
							.getElementsByTagName("description");
					len = descriptionTag.getLength();

					strDefinition1 = "";
					Element description = (Element) descriptionTag.item(0);

					// ---get all the child nodes under the
					// <description> element---
					NodeList textNodes1 = ((Node) description).getChildNodes();

					// ---get the first node, which contains the text---
					strDefinition1 += ((Node) textNodes1.item(0))
							.getNodeValue() + ". ";

					// ---display the title and description---
					//Toast.makeText(getBaseContext(),
						//	strDefinition + strDefinition1, Toast.LENGTH_SHORT)
							//.show();
					
					feedlist.add(new td(strDefinition,strDefinition1));
					//feedlist.add(strDefinition);
					
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG)
					.show();
			e.printStackTrace();
		}
	return feedlist;}

	private InputStream OpenHttpConnection(String UrlString) throws IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		int response = -1;

		URL url = new URL(UrlString);
		URLConnection connection = url.openConnection();

		if (!(connection instanceof HttpURLConnection))
			throw new IOException("Not a HTTP connection");
		try {
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setAllowUserInteraction(false);
			httpURLConnection.setInstanceFollowRedirects(true);
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.connect();
			response = httpURLConnection.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				inputStream = httpURLConnection.getInputStream();
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new IOException("Error connecting");
		}
		return inputStream;
	}
}
