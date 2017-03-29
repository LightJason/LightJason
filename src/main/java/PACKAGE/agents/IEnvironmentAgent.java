package {{{ package }}}.agents;

import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
{{ #environmentactionexist }}
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
{{ /environmentactionexist }}
import {{{ package }}}.environment.IEnvironment;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * abstract class to define an
 * agent with environment
 */
{{ #environmentactionexist }}@IAgentAction{{ /environmentactionexist }}
public abstract class IEnvironmentAgent<T extends IEnvironmentAgent<?>> extends IBaseAgent<IEnvironmentAgent<T>>
{
    /**
     * reference to environment
     */
    protected final IEnvironment m_environment;
    /**
     * agent name
     */
    private final String m_name;    

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_name agent name
     */
    protected IEnvironmentAgent( final IAgentConfiguration<IEnvironmentAgent<T>> p_configuration, final IEnvironment p_environment, final String p_name )
    {
        super( p_configuration );
        m_name = p_name;
        m_environment = p_environment;

        // add environment beliefbase to the agent with the prefix "env"
        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
    }

    /**
     * returns a literal stream to get a view
     * of the agent depends on the calling agent
     *
     * @param p_agent calling agent
     * @return literal stream
     */
    public Stream<ILiteral> literal( final IEnvironmentAgent<?> p_agent )
    {
        return Stream.of( 
                CLiteral.from( "agent", 
                               CLiteral.from( "name", CRawTerm.from( m_name ) ) 
                ) 
        );
    }

        /**
     * returns the agent name
     *
     * @return agent name
     */
    public final String name()
    {
        return m_name;
    }

    @Override
    public final int hashCode()
    {
        return m_name.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IEnvironmentAgent<?> ) && ( p_object.hashCode() == this.hashCode() );
    }


    {{ #environmentactionlist }}
    @IAgentActionFilter
    @IAgentActionName( name = "{{{ name }}}" )
    private {{{ return }}} {{{ name }}}( {{{ argument }}} )
    {
        {{ #passreturn }}return{{ /passreturn }} m_environment.{{{ name }}}( this{{ #passargument }}, {{{ passargument }}} {{ /passargument }} );
    }

    {{ /environmentactionlist }}


    /**
     * on-demand beliefbase to get access
     * to the environment data
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<IEnvironmentAgent<T>>
    {

        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_environment.literal( IEnvironmentAgent.this );
        }

        @Override
        public final Collection<ILiteral> literal( final String p_key )
        {
            return m_environment.literal( IEnvironmentAgent.this )
                                .filter(i -> p_key.equals( i.functor() ) )
                                .collect( Collectors.toSet() );
        }

        @Override
        public final boolean empty()
        {
            return !m_environment.literal( IEnvironmentAgent.this )
                                 .findFirst()
                                 .isPresent();
        }

        @Override
        public final int size()
        {
            return (int) m_environment.literal( IEnvironmentAgent.this ).count();
        }

        @Override
        public final boolean containsLiteral( final String p_key)
        {
            return m_environment.literal( IEnvironmentAgent.this )
                                .anyMatch(i -> p_key.equals( i.functor() ) );
        }

    }
}
