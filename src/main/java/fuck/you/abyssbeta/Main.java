package fuck.you.abyssbeta;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@Mod(
        modid = Main.MODID,
        name = Main.NAME,
        version = Main.VERSION
)
public class Main
{
    public static final String MODID = "abyssbeta";
    public static final String NAME = "Abyss Beta Enforcer";
    public static final String VERSION = "1.0";

    public static final Logger logger;

    static
    {
        logger = LogManager.getLogger( NAME );

        logger.info( "{} by mrnv/ayywareseller (github.com/PlutoSolutions)", NAME );

        try
        {
            // Class.forName throws an exception if the class wasn't found
            Class.forName( "com.cout970.rocketdrm.RocketDRM", false, Launch.classLoader );

            HashMap data = ( HashMap )Launch.blackboard.get( "DRM-InjectData" );
            if( data != null )
            {
                String info = ( String )data.get( "Abyss-Info" );
                if( info != null )
                {
                    String decrypted = Utils.decrypt( info );
                    if( decrypted != null )
                    {
                        JsonObject object = new JsonParser( ).parse( decrypted ).getAsJsonObject( );
                        JsonArray roles = object.get( "roles" ).getAsJsonArray( );

                        boolean beta = false;
                        for( JsonElement element : roles )
                        {
                            if( element.getAsString( ).equals( "beta" ) )
                            {
                                logger.info( "You already have beta???????????" );
                                beta = true;
                                break;
                            }
                        }

                        if( !beta )
                        {
                            logger.info( "Setting beta" );
                            object.get( "roles" ).getAsJsonArray( ).add( "beta" );

                            logger.info( "Encrypting new data" );

                            String modified = object.toString( );
                            String encrypted = Utils.encrypt( modified );

                            data.remove( "Abyss-Info" );
                            data.put( "Abyss-Info", encrypted );

                            logger.info( "Done" );
                        }
                    }
                    else
                        logger.info( "Failed to decrypt Abyss data" );
                }
                else
                    logger.info( "Failed to find Abyss data in Rocket DRM storage" );
            }
        }
        catch( Exception e )
        {
            if( e instanceof ClassNotFoundException )
                logger.error( "You don't have Rocket DRM installed" );
            else
                e.printStackTrace( );
        }
    }
}
