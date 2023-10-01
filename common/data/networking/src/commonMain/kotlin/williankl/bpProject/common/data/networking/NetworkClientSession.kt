package williankl.bpProject.common.data.networking

public interface NetworkClientSession {
    public fun currentToken(): String?
    public fun attachBearer(token: String)
    public fun clearBearer()
}