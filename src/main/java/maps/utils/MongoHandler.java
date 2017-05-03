package maps.utils;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.*;

import maps.route.Edge;
import maps.route.MinPath;

public class MongoHandler {
	
	private static MongoHandler instance = null;
	
	private MongoClient mc;
	private MongoDatabase md;
	
	public static MongoHandler getInstance(String db){
		if(instance == null){
			instance = new MongoHandler(db);
		}
		return instance;
	}
	
	private MongoHandler(String db){
		mc = new MongoClient("localhost", 27017);
		md = mc.getDatabase(db);
	}
	
	public MinPath getMinPath(Document d)
	{
		MinPath mp = new MinPath();
		
		ArrayList<Edge> edges = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Document> doc_edges = (List<Document>) d.get("path");
		for ( Document edge : doc_edges )
		{
			edges.add(
					new Edge(
								edge.getString("busStopID"),
								edge.getString("line"),
								edge.getInteger("cost")
							));
		}
		mp.setEdges(edges);
		mp.setIdSource(d.getString("sourceID"));
		mp.setIdDestination(d.getString("destinationID"));
		mp.setTotalCost(d.getInteger("cost"));
		
		return mp;
	}
	
	public MinPath getMinPath(String idSource , String idDestination, int minCost)
	{		
		MongoCursor<Document> cursor =  md.getCollection("percorsi")
					.find(
							com.mongodb.client.model.Filters.and(
							com.mongodb.client.model.Filters.eq("sourceID", idSource), 
							com.mongodb.client.model.Filters.eq("destinationID", idDestination),
							com.mongodb.client.model.Filters.lt("cost", minCost)
						)
					).iterator();
		
		Document d = cursor.next();
		
		return getMinPath(d);
	}
}
